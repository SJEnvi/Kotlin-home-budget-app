package com.example.projektfinalny

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.identity.BeginSignInRequest
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
import javax.sql.DataSource

class SettingsFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //First we need to inflate view
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        //Then we can use that view for other actions
        val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)
        val dummyBtn = view.findViewById<Button>(R.id.addDummy)
        val buttonSQL = view.findViewById<Button>(R.id.buttonSQL)
        val testText = view.findViewById<TextView>(R.id.testTv)



        //assign functions to buttons
        deleteBtn?.setOnClickListener { deleteAllTransactions() }
        dummyBtn?.setOnClickListener { prepareDummyTransactionsFile() }
        buttonSQL?.setOnClickListener {
            var signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build()
                )
                .build()


            }

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

//            CoroutineScope(Dispatchers.IO).launch {
//                val connector = Hikari()
//                val connection = connector.conclass()
//                if(connector!=null){
//                    val sqlStatement = "Select * from emails"
//                    var smt: Statement=connection.createStatement()
//                    var set= smt.executeQuery(sqlStatement)
//                    while (set.next()){
//                        Toast.makeText(requireContext(), set.getString(1), Toast.LENGTH_SHORT).show()
//                    }
//                    connection.close()
//                }
//                val pool: DataSource = connection.createConnectionPool()L/application_default_credentials.json")
//                    pool.getConnection().use { conn ->
//
//                try {
//                    val path = System.getProperty("user.dir")
//                    System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "$path/SQ
//                        val statement = "SELECT * FROM emails"
//                        val stmt: PreparedStatement = conn.prepareStatement(statement)
//                        val rs: ResultSet = stmt.executeQuery()
//                        while (rs.next()) {
//                            val email: String = rs.getString("email")
//                            System.out.printf("email: %s\n", email)
//                        }
//                    }
//                } catch (ex: SQLException) {
//                    System.err.println("SQLException: ")
//                }

//                val sqlConnection = SqlConnection()
//                val conn = sqlConnection.connect()
//
//// Use the connection here
//                val statement = conn.createStatement()
//                val resultSet = statement.executeQuery("SELECT * FROM emails")
//
//                while (resultSet.next()) {
//                    val columnValue = resultSet.getString("email")
//                    Toast.makeText(requireContext(), columnValue, Toast.LENGTH_SHORT).show()
//                }
////      It's important to close the below to avoid resource leaks
//                resultSet.close()
//                statement.close()
//                conn.close()
//            }


//and finally return the view