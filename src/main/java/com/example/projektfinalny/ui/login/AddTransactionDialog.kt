package com.example.projektfinalny.ui.login

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import com.example.projektfinalny.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionDialog : DialogFragment() {

    var selectedItem : String? = null
    @RequiresApi(Build.VERSION_CODES.O)

    //firestore test
    val db = Firebase.firestore
    lateinit var timestamp: Timestamp


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new AlertDialog.Builder object
        val builder = AlertDialog.Builder(activity)
        timestamp = Timestamp.now()

        // Set the dialog title
        builder.setTitle("Add new transaction")

        val layout = LinearLayout(activity)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50,50,50,50)

        // Set the dialog message and input field
        val title = EditText(activity)
        title.hint = "Enter title"
        layout.addView(title)

        val amount = EditText(activity)
        amount.hint = "Enter amount (separate with dot)"
        layout.addView(amount)

        val categoryText = TextView(activity)
        categoryText.text = "Select category"
        categoryText.setPadding(20)
        layout.addView(categoryText)

        val category = Spinner(activity)
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.CategoryList, android.R.layout.simple_spinner_item)
        category.adapter = adapter
        layout.addView(category)

        val dateButton = Button(activity)
        dateButton.text = "Select date"
        dateButton.setOnClickListener { clickDate() }
        layout.addView(dateButton)

        builder.setView(layout)


        category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedItem = "No category"
            }
        }

        // Set the positive button text and click listener
        builder.setPositiveButton("OK") { _, _ ->
            // Get the input text and do something with it
            val amountRounded = roundTo2Dec(amount.text.toString().toDouble())

            val transaction = hashMapOf(
                "title" to title.text.toString(),
                "amount" to amountRounded,
                "category" to selectedItem,
                "date" to timestamp
            )

            val loggedInUser = FirebaseAuth.getInstance().currentUser?.email
            val mainCollection = db.collection("users")
            mainCollection.document(loggedInUser!!).collection("transactions").add(transaction)
            }

        // Set the negative button text and click listener
        builder.setNegativeButton("Cancel") { _, _ ->
            // Do nothing
        }

        // Return the created dialog
        return builder.create()
    }

    private fun clickDate(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(),
            DatePickerDialog.OnDateSetListener{ _, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                Toast.makeText(requireContext(), selectedDate, Toast.LENGTH_LONG).show()

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val dateParsed = sdf.parse(selectedDate)
                timestamp = Timestamp(dateParsed!!)
                Toast.makeText(requireContext(), timestamp.toString(),Toast.LENGTH_LONG).show()
                                              },
            year,
            month,
            day
        )
        dpd.show()


    }

    private fun roundTo2Dec(number: Double) : Double{return (kotlin.math.round(number*100)/100)}

}