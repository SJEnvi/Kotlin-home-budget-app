package com.example.projektfinalny.ui.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.CurrencyAmount
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.setPadding
import com.example.projektfinalny.R
import com.example.projektfinalny.data.model.Transaction
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class UpdateActivity : AppCompatActivity() {

    lateinit var selectedCat: String



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        var transaction: Transaction = intent.getSerializableExtra("transaction") as Transaction

        val updateBtn = findViewById<Button>(R.id.button)
        val amountText = findViewById<EditText>(R.id.editTextNumberDecimal)
        val titleText = findViewById<EditText>(R.id.editTextTextPersonName)
        val dateText = findViewById<TextView>(R.id.editTextDate)
        val buttonDate = findViewById<Button>(R.id.buttonDate)

        val spinnerCat = findViewById<Spinner>(R.id.updateSpinner)
        val adapterCat = ArrayAdapter.createFromResource(
            this,
            R.array.CategoryList,
            android.R.layout.simple_spinner_item
        )
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCat.adapter = adapterCat

        val position = adapterCat.getPosition(transaction.category)
        spinnerCat.setSelection(position)

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

        buttonDate.setOnClickListener { clickDate(dateText) }


        amountText.setText(transaction.amount.toString())
        titleText.setText(transaction.title)
        dateText.setText(transaction.date.toString())

        updateBtn.setOnClickListener {
            var newAmount = amountText.text.toString().toDouble()
            var newTitle = titleText.text.toString()
            var newDate = Date(dateText.text.toString())
            updateTransaction(newAmount, newTitle, newDate, transaction.id, selectedCat)}

    }

    fun updateTransaction(amount: Double, title: String, date: Date, id: String, category: String){

        val db = Firebase.firestore
        val loggedInUser = FirebaseAuth.getInstance().currentUser?.email
        val mainCollection = db.collection("users").document(loggedInUser!!)
            .collection("transactions")
            .document(id)

        val transaction = hashMapOf(
                "title" to title,
                "amount" to amount,
                "category" to category,
                "date" to date
            )

        mainCollection.set(transaction)
        finish()
    }

    private fun clickDate(view: TextView){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ _, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"


                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val dateParsed = sdf.parse(selectedDate)
                view.text = Timestamp(dateParsed!!).toDate().toString()
            },
            year,
            month,
            day
        )
        dpd.show()


    }
}




