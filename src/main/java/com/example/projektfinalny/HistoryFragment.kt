package com.example.projektfinalny

import android.annotation.SuppressLint
import android.app.DownloadManager.Query
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.BadTokenException
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projektfinalny.data.model.LoggedInUser
import com.example.projektfinalny.ui.login.MyAdapter
import com.example.projektfinalny.data.model.Transaction
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class HistoryFragment : Fragment() {
    //declaring variables I will use later on - in onCreate
    lateinit var newRecyclerView: RecyclerView
    private val transactionsList: ArrayList<Transaction> = ArrayList(1)
    lateinit var selectedTime: String
    lateinit var selectedCat: String
    lateinit var db: FirebaseFirestore
    lateinit var loggedInUser: String
    lateinit var transactionsCollection: CollectionReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        //Assigning button view to reference
        val applyBtn = view.findViewById<Button>(R.id.applyFiltersBtn)

        //initializing spinner with time selection
        val spinnerTime = view.findViewById<Spinner>(R.id.timeSelectorSpinner)
        val adapterTime = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.timeList,
            android.R.layout.simple_spinner_item
        )
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTime.adapter = adapterTime

        //initializing spinner with category selection
        val spinnerCat = view.findViewById<Spinner>(R.id.catSelectorSpinner)
        val adapterCat = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.CategoryList,
            android.R.layout.simple_spinner_item
        )
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCat.adapter = adapterCat

        //initializing recycler view
        newRecyclerView = view.findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(view?.context)
        newRecyclerView.setHasFixedSize(true)


        spinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected item
                selectedTime = parent.getItemAtPosition(position) as String

                // Check if the selected item is different from a previous selection

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing when no item is selected
            }
        }


        spinnerCat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected item
                selectedCat = parent.getItemAtPosition(position) as String

                // Check if the selected item is different from a previous selection

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing when no item is selected
            }
        }


        applyBtn.setOnClickListener {
            transactionsList.clear()
            newRecyclerView.adapter = MyAdapter(getTransactionData(selectedTime, selectedCat))
        }

        return view
    }


    private fun getTransactionData(timePeriod: String?, category: String?): ArrayList<Transaction> {
        //clearing the transactions list in case the user opens this fragment multiple times
        db = Firebase.firestore
        loggedInUser = FirebaseAuth.getInstance().currentUser?.email!!
        transactionsCollection =
            db.collection("users").document(loggedInUser!!).collection("transactions")

        var query = prepareQuery(timePeriod, category)

        addToTransactionList(query)
        return transactionsList
    }


        private fun addToTransactionList(query: com.google.firebase.firestore.Query) {
            query.get()
                .addOnSuccessListener { collection ->
                    for (data in collection) {
                        try {
                            val timestamp = data.getTimestamp("date")!!
                            var transaction = Transaction(
                                data.id,
                                data.getString("title")!!,
                                data.getDouble("amount")!!,
                                data.getString("category")!!,
                                timestamp.toDate()
                            )
                            transactionsList.add(transaction)


                            //lastly we add the object to list of Transactions
                        }catch (e: Error){
                            Toast.makeText(requireContext(), "failed to get firestore data: $e", Toast.LENGTH_LONG).show()
                        }
                    }
                    newRecyclerView.adapter?.notifyDataSetChanged()
                }

    }

    fun prepareQuery(timePeriod: String?, category: String?): com.google.firebase.firestore.Query{
        val calendar = Calendar.getInstance()
        val timestampNow = Timestamp(calendar.time)
//        if (category!="All"){
//            query = query.whereEqualTo("category", category)
//        }

        //applying time filters to the query
        when (timePeriod) {
            "Last Month" -> calendar.add(Calendar.MONTH, -1)
            "Last Week" -> calendar.add(Calendar.DAY_OF_MONTH, -7)
        }
        val timestampFiler = Timestamp(calendar.time)

        var query = when (timePeriod) {
            "All" -> transactionsCollection.whereLessThanOrEqualTo("date", timestampNow)
            else -> transactionsCollection.whereLessThanOrEqualTo("date", timestampNow)
                .whereGreaterThanOrEqualTo("date", timestampFiler)
//                .whereEqualTo("category", category)
        }

//        applying category filters
        if (category!="Wszystkie") {
            query = query.whereEqualTo("category", category!!)
        }
        return query
    }

}