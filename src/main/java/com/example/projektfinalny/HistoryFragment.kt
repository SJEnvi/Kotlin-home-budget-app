package com.example.projektfinalny

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projektfinalny.ui.login.MyAdapter
import com.example.projektfinalny.data.model.Transaction
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class HistoryFragment : Fragment() {
    //declaring variables I will use later on - in onCreate
    lateinit var newRecyclerView: RecyclerView
    val FILE_NAME = "transactions.txt"
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val transactionsList : ArrayList<Transaction> = ArrayList(5)
    var selectedItem: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_history, container, false)

        //initializing spinner with time selection
        val spinner = view.findViewById<Spinner>(R.id.timeSelectorSpinner)
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.timeList, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //initializing recycler view
        newRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        newRecyclerView?.layoutManager = LinearLayoutManager(view?.context)
        newRecyclerView?.setHasFixedSize(true)


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Get the selected item
                selectedItem = parent.getItemAtPosition(position) as String

                // Check if the selected item is different from a previous selection
                if (selectedItem != "Item 1") {
                    // Do something when a different item is selected
                    Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_SHORT).show()
                }
                newRecyclerView.adapter = MyAdapter(getTransactionData(selectedItem))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing when no item is selected
            }
        }

        //then populate it with data
        newRecyclerView.adapter = MyAdapter(getTransactionData())



        return view
    }

    //TODO Replace this function with the one that gets data from file
    private fun getTransactionData(): ArrayList<Transaction>{
        return getTransactionData("All")
    }

    private fun getTransactionData(timePeriod: String?): ArrayList<Transaction> {
        //clearing the transactions list in case the user opens this fragment multiple times
        transactionsList.clear()

        //reading data from the file
        val inputStream = context?.openFileInput(FILE_NAME)
        val buffer = ByteArray(inputStream?.available() ?: 0)
        inputStream?.read(buffer)
        //note it's saved as single string
        val fileText = String(buffer)
        // thus we first split into list of lines
        var fileTextSeparated = fileText.split("\n")
        addToTransactionList(fileTextSeparated, timePeriod)

        //then we take every line and create a Transaction object out of the data in it

        return transactionsList
    }
    private fun addToTransactionList(separatedData : List<String>, timePeriod: String?): ArrayList<Transaction> {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        when (timePeriod){
            "All" -> calendar.add(Calendar.YEAR, -20)
            "Last Month" -> calendar.add(Calendar.MONTH, -1)
            "Last Week" -> calendar.add(Calendar.WEEK_OF_YEAR, -1)
        }
        for (data in separatedData) {
                try {
                    var transactionData = data.split(",")
                    val transactionDate = dateFormat.parse(transactionData[4])
                    if (calendar.time < transactionDate) {
                            var transaction = Transaction(
                            transactionData[0].toInt(),
                            transactionData[1],
                            transactionData[2].toDouble(),
                            transactionData[3],
                            transactionDate
                        )
                        transactionsList.add(transaction)
                    }
                    //lastly we add the object to list of Transactions
                }catch (e: Error){ }
            }
            //finally send the data to recyclerview adapter
        return transactionsList

    }
}