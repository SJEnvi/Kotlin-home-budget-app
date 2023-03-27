package com.example.projektfinalny

import MyDialogFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.projektfinalny.data.model.Transaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*

class HomeFragment : Fragment() {
    val FILE_NAME = "transactions.txt"
    var plus = 0.0
    var minus = 0.0
//    var homeFragment: HomeFragment = HomeFragment()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val addTransButton = view.findViewById<FloatingActionButton>(R.id.add_transaction_btn)
        val balance = view.findViewById<TextView>(R.id.balance)
        val expenses = view.findViewById<TextView>(R.id.expense_amount)
        val incomes = view.findViewById<TextView>(R.id.budget_amount)


        try {
            plus = 0.0
            minus = 0.0
            val loggedInUser = FirebaseAuth.getInstance().currentUser?.email!!
            var db = Firebase.firestore
            var collectionRef =
                db.collection("users").document(loggedInUser!!).collection("transactions")
            //getting all documents with transactions
            collectionRef.get()
                .addOnSuccessListener { collection ->
                    for (data in collection) {
                        try {
                            var amount = data.getDouble("amount")!!
                            if (amount >= 0) {
                                plus += amount
                            } else {
                                minus += amount
                            }
                        } catch (e: Error) {
                            Toast.makeText(
                                requireContext(),
                                "failed to get firestore data: $e",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    var sum = minus + plus
                    if (sum >= 0) {
                        balance.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.balanceGreen
                            )
                        )
                    } else {
                        balance.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    }
                    balance.text = roundTo2Dec(sum).toString() + " zł"
                    expenses.text = roundTo2Dec(minus).toString() + " zł"
                    incomes.text = roundTo2Dec(plus).toString() + " zł"
                }
            try {

            } catch (e: Error) {
                Toast.makeText(requireContext(), "error: $e", Toast.LENGTH_LONG).show()
            }
        }catch (e: NullPointerException) {
            Toast.makeText(requireContext(), "error: $e", Toast.LENGTH_LONG).show()
        }
        //Created on click listener that will open "MyDialogFragment" if button is clicked
        addTransButton.setOnClickListener {
            val dialog = MyDialogFragment()
            dialog.show(parentFragmentManager, "MyDialogFragment")
        }

//        onActivityResult(1, 1, intent){
//
//        }





        return view
    }

//    private fun onActivityResult(i: Int, i1: Int, intent: Intent, function: () -> Unit) {
//        super.onActivityResult(i, i1, intent)
//        val balance = view.findViewById<TextView>(R.id.balance)
//        val expenses = view.findViewById<TextView>(R.id.expense_amount)
//        val incomes = view.findViewById<TextView>(R.id.budget_amount)
//        readBalance()
//    }

    //TODO Create a function that will read the balance
    private fun readBalance(callback: (List<Double>) -> Unit){

    }
//    private fun readBalance(): List<Double> {
//        var fileTextSeparated: List<String>? = null
//        //added try block in case the file does not exist
//        try {
//            val inputStream = context?.openFileInput(FILE_NAME)
//            val buffer = ByteArray(inputStream?.available() ?: 0)
//            inputStream?.read(buffer)
//            //note it's saved as single string
//            val fileText = String(buffer)
//            // thus we first split into list of lines
//            fileTextSeparated = fileText.split("\n")
//        } catch (e: Error) { }
//        //here I declare variables that will store whole balance
//        var adds = 0.0
//        var minus = 0.0
//
//        //then we take every line and create and sum all numbers
//        if (fileTextSeparated != null) {
//            for (data in fileTextSeparated) {
//                var transactionData = data.split(",")
//                var amount = transactionData[2].toDouble()
//                if (amount < 0){
//                    minus += amount
//                }
//                else{
//                    adds += amount
//                }
//            }
//        }
//        return listOf(minus, adds)


    private fun roundTo2Dec(number: Double) : Double{return (kotlin.math.round(number*100)/100)}
}