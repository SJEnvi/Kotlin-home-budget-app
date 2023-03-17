package com.example.projektfinalny

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.io.*


class MainActivity constructor() : AppCompatActivity() {

    //    var bottomNavigationView: BottomNavigationView = BottomNavigationView(this)
    var homeFragment: HomeFragment = HomeFragment()
    var historyFragment: HistoryFragment = HistoryFragment()
    var analyzeFragment: AnalyzeFragment = AnalyzeFragment()
    var settingsFragment: SettingsFragment = SettingsFragment()

    val RC_SIGN_IN = 20202

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        val bottomNavigationView = findViewById<BottomNavigationView?>(R.id.bottom_navigation)
        homeFragment.let {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, it)
                .commit()
        }

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

        bottomNavigationView.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            public override fun onNavigationItemSelected(item: android.view.MenuItem): kotlin.Boolean {
                when (item.getItemId()) {
                    R.id.home -> {
                        getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, homeFragment).commit()
                        return true
                    }
                    R.id.history -> {
                        getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, historyFragment).commit()
                        title
                        return true
                    }
                    R.id.analyze -> {
                        getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, analyzeFragment).commit()
                        return true
                    }
                    R.id.settings -> {
                        getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, settingsFragment).commit()
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.email
                // Signed in successfully, do something with the account
                Toast.makeText(this, idToken.toString(), Toast.LENGTH_SHORT).show()

            } catch (e: ApiException) {
                // Sign in failed, handle the error
                Log.w(TAG, "Google sign in failed", e)
                val message = "Google sign in failed: ${e.statusCode} ${e.localizedMessage}"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

}