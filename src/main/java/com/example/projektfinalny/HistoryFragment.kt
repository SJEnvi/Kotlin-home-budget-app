package com.example.projektfinalny

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projektfinalny.ui.login.MyAdapter
import com.example.projektfinalny.data.model.Transaction
import java.io.File

class HistoryFragment : Fragment() {
    //declaring variables I will use later on - in onCreate
    lateinit var newRecyclerView: RecyclerView
    val FILE_NAME = "transactions.txt"
    private val transactionsList : ArrayList<Transaction> = ArrayList(5)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_history, container, false)
        //initializing recycler view
        newRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        newRecyclerView?.layoutManager = LinearLayoutManager(view?.context)
        newRecyclerView?.setHasFixedSize(true)

        //then populate it with data
        newRecyclerView.adapter = MyAdapter(getTransactionData())


        return view
    }

    //TODO Replace this function with the one that gets data from file
    private fun getTransactionData(): ArrayList<Transaction> {
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

        //then we take every line and create a Transaction object out of the data in it
        for (data in fileTextSeparated) {
            try {
                var transactionData = data.split(",")
                var transaction = Transaction(
                    transactionData[0].toInt(),
                    transactionData[1],
                    transactionData[2].toDouble(),
                    transactionData[3]
                )
                //lastly we add the object to list of Transactions
                transactionsList.add(transaction)
            }catch (e: Error){

            }



            //finally send the data to recyclerview adapter
        }
        return transactionsList
    }
}