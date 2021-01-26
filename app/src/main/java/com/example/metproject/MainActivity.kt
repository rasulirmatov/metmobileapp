package com.example.metproject

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.forEach
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var  appSettingPrefs: SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
//        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
//        window.statusBarColor = ContextCompat.getColor(this,R.color.white);// set status background white
        appSettingPrefs = getSharedPreferences("AppSettingPrefs", Context.MODE_PRIVATE)
//        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        // Disable Android OS Default Nigth Mode
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        val currentNightMode = (resources.configuration.uiMode
//                and Configuration.UI_MODE_NIGHT_MASK)
//
//        when (currentNightMode) {
//            Configuration.UI_MODE_NIGHT_NO -> {
//                Toast.makeText(applicationContext, "Day", Toast.LENGTH_SHORT).show()
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            } // Night mode is not active, we're using the light theme
//            Configuration.UI_MODE_NIGHT_YES -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                Toast.makeText(applicationContext, "Night", Toast.LENGTH_SHORT).show()
//            } // Night mode is active, we're using dark theme
//        }


        // Bottom Navigation Visibility duration
        bottomNavigationHandler(500)

        bottomNavigationView.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.myNavHostFragment
            )
        )

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            // Bottom Navigation Visibility duration
            bottomNavigationHandler(0)

            when (item.itemId) {
                R.id.home -> {
//                    Toast.makeText(applicationContext, "Home", Toast.LENGTH_SHORT).show()
//                    findNavController(R.id.myNavHostFragment).popBackStack()
                    findNavController(R.id.myNavHostFragment).popBackStack(R.id.classes, true)
                    findNavController(R.id.myNavHostFragment).popBackStack(R.id.home, true)
                    findNavController(R.id.myNavHostFragment).popBackStack(R.id.subjects, true)
                    findNavController(R.id.myNavHostFragment)
                        .navigate(
                            R.id.home,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(
                                    R.id.splashScreenFragment,
                                    true
                                ).build()
                        )
                }
                R.id.subjects -> {
//                    Toast.makeText(applicationContext, "Subjects", Toast.LENGTH_SHORT).show()
//                    findNavController(R.id.myNavHostFragment).popBackStack()
                    findNavController(R.id.myNavHostFragment).popBackStack(R.id.subjects, true)
                    findNavController(R.id.myNavHostFragment).popBackStack(R.id.classes, true)
                    findNavController(R.id.myNavHostFragment)
                        .navigate(
                            R.id.subjects,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(
                                    R.id.myNavHostFragment,
                                    true
                                ).build()
                        )
                }
                R.id.classes -> {
//                    Toast.makeText(applicationContext, "Classes", Toast.LENGTH_SHORT).show()
//                    findNavController(R.id.myNavHostFragment).popBackStack()
                    findNavController(R.id.myNavHostFragment).popBackStack(R.id.classes, true)
                    findNavController(R.id.myNavHostFragment).popBackStack(R.id.subjects, true)
                    findNavController(R.id.myNavHostFragment)
                        .navigate(
                            R.id.classes,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(
                                    R.id.splashScreenFragment,
                                    true
                                ).build()
                        )
                }
            }
            true
        }

    }

    private fun bottomNavigationHandler(duration: Long = 0L) {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility = View.GONE
            bottomNavigationView.menu.forEach {
                if (it.itemId == destination.id || destination.id == R.id.home) {
                    if (duration == 0L) {
                        bottomNavigationView.visibility = View.VISIBLE
                    } else {
                        Handler().postDelayed({
                            bottomNavigationView.visibility = View.VISIBLE
                        }, duration)
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

}
