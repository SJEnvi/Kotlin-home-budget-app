package com.example.projektfinalny

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import java.io.File

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //First we need to inflate view
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        //Then we can use that view for other actions
        val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)
        val dummyBtn = view.findViewById<Button>(R.id.addDummy)

        //assign functions to buttons
        deleteBtn?.setOnClickListener { deleteAllTransactions() }
        dummyBtn?.setOnClickListener { prepareDummyTransactionsFile() }

        //and finally return the view
        return view
    }

    private fun deleteAllTransactions() {
        val file = File(requireContext().filesDir, "transactions.txt")
        if (file.exists()) {
            file.delete()
            Toast.makeText(requireContext(), "File deleted", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "File does not exist", Toast.LENGTH_LONG).show()
        }
    }

    private fun prepareDummyTransactionsFile() {
        val dummyList = """1,Test1,10.0,Rachunki,2005-07-03
            |2,Test2,20.0,Rachunki,2023-02-20
            |3,Test3,30.0,Rachunki,2023-03-09
        """.trimMargin()
        requireContext().openFileOutput("transactions.txt", Context.MODE_PRIVATE).use {
            it.write(dummyList.toByteArray())
        }
    }
}