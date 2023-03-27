package com.example.projektfinalny


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth


class MainActivity constructor() : AppCompatActivity() {

    //    var bottomNavigationView: BottomNavigationView = BottomNavigationView(this)
    var homeFragment: HomeFragment = HomeFragment()
    var historyFragment: HistoryFragment = HistoryFragment()
    var analyzeFragment: AnalyzeFragment = AnalyzeFragment()
    var settingsFragment: SettingsFragment = SettingsFragment()
//    val REQUEST_CODE = 1


    private val RC_SIGN_IN = 20202
    private val REQUEST_CODE_REFRESH = 1

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            // Do something when the activity returns a result
//        }
//    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Start GoogleAuth activity

        if (FirebaseAuth.getInstance().currentUser?.email==null){
            val intent = Intent(this, GoogleAuth::class.java)
            startActivity(intent)
        }






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