package com.example.projektfinalny

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.text.SimpleDateFormat
import java.util.*
import javax.sql.DataSource

class SettingsFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //First we need to inflate view
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val startDate = view.findViewById<Button>(R.id.startDate)
        val numberOfMonths = view.findViewById<EditText>(R.id.numberOfMonths)
        val amountEveryMonth = view.findViewById<EditText>(R.id.amountEveryMonth)
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)

        startDate.setOnClickListener { clickDate(startDate) }



        confirmButton.setOnClickListener {
            val startDt = Date(startDate.text.toString())
            val numMonths = numberOfMonths.text.toString().toInt()
            val amount = amountEveryMonth.text.toString().toDouble()
            sendDataToFirestore(startDt, numMonths, amount)
            numberOfMonths.text.clear()
            amountEveryMonth.text.clear()
            startDate.text = "Kliknij by ustawić datę"

        }




        return view
    }

    private fun sendDataToFirestore(startDt: Date, numMonths: Int, amount: Double) {
        val db = Firebase.firestore
        val loggedInUser = FirebaseAuth.getInstance().currentUser?.email
        val date = Calendar.getInstance()
        date.time = startDt
        val mainCollection = db.collection("users").document(loggedInUser!!)
            .collection("transactions")

        for (i in 1..numMonths){


            val transaction = hashMapOf(
                "title" to "Stały zarobek",
                "amount" to amount,
                "category" to "Wplywy",
                "date" to Timestamp(date.time)
            )
            date.add(Calendar.MONTH, 1)
            mainCollection.document("MonthlyTransaction$i").set(transaction)
        }
        Toast.makeText(requireContext(), "Pomyślnie dodano zarobek do bazy danych", Toast.LENGTH_LONG).show()

    }

    private fun clickDate(view: Button){

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
                view.text = Timestamp(dateParsed!!).toDate().toString()
            },
            year,
            month,
            day
        )
        dpd.show()


    }

}

