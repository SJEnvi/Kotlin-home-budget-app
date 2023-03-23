package com.example.projektfinalny


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView



class MainActivity constructor() : AppCompatActivity() {

    //    var bottomNavigationView: BottomNavigationView = BottomNavigationView(this)
    var homeFragment: HomeFragment = HomeFragment()
    var historyFragment: HistoryFragment = HistoryFragment()
    var analyzeFragment: AnalyzeFragment = AnalyzeFragment()
    var settingsFragment: SettingsFragment = SettingsFragment()


    val RC_SIGN_IN = 20202

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, GoogleAuth::class.java)
        startActivity(intent)
        setContentView(R.layout.activity_main)

        // ...
// Initialize Firebase Auth




        val bottomNavigationView = findViewById<BottomNavigationView?>(R.id.bottom_navigation)
        homeFragment.let {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, it)
                .commit()
        }


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
}