package com.example.projektfinalny

import MyDialogFragment
import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*

class HomeFragment : Fragment() {

    val FILE_NAME = "transactions.txt"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val FILE_NAME = "transactions.txt"
        // Checks if the transactions file already exists
        val fileExists = context?.getFileStreamPath(FILE_NAME)?.exists() ?: false
        //
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        //TODO Create an add button function that saves transaction details to a file
        val addTransButton = view.findViewById<FloatingActionButton>(R.id.add_transaction_btn)
        val textTv = view.findViewById<TextView>(R.id.testTV)
        addTransButton.setOnClickListener {

            val fileContents = if (fileExists) {"\nI jeszcze piwo!"}else{"Kup chleb!"}

            context?.openFileOutput(FILE_NAME, Context.MODE_APPEND).use {
                it?.write(fileContents.toByteArray())
            }

            val inputStream = context?.openFileInput(FILE_NAME)
            val buffer = ByteArray(inputStream?.available() ?: 0)
            inputStream?.read(buffer)
            val fileText = String(buffer)
            Toast.makeText(context, fileText, Toast.LENGTH_LONG).show()
            textTv.text = fileText

            val dialog = MyDialogFragment()
            dialog.show(parentFragmentManager, "MyDialogFragment")
        }

        return view


//            mEditText.getText().clear()
//            Toast.makeText(
//                this, "Zapisane w ${context?.filesDir}/$FILE_NAME",
//                Toast.LENGTH_LONG
//            ).show()
        Toast.makeText(context, "Udalo sie", Toast.LENGTH_LONG).show()
        //TODO Create a function that will save Trasactions to file
    }
}

//    fun addTransaction(view: View) {

//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//        var fis: FileInputStream? = null
//
//        try {
//            fis = context?.openFileInput(FILE_NAME)
//            val isr = InputStreamReader(fis)
//            val br = BufferedReader(isr)
//            val sb = StringBuilder()
//            var text: String?
//            while (br.readLine().also { text = it } != null) {
//                sb.append(text).append("\n")
//            }
//            testTv?.text = sb.toString()
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }
//}