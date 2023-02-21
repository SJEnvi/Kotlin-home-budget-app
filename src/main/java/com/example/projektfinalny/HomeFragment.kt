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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        //TODO Create an add button function that saves transaction details to a file
        val addTransButton = view.findViewById<FloatingActionButton>(R.id.add_transaction_btn)
//        val textTv = view.findViewById<TextView>(R.id.testTV)
        addTransButton.setOnClickListener {


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