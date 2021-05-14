package com.example.metproject

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.forEach
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var appSettingPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
//        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
//        window.statusBarColor = ContextCompat.getColor(this,R.color.white);// set status background white

        appSettingPrefs = getSharedPreferences("AppSettingPrefs", Context.MODE_PRIVATE)
//        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        if (isNightModeOn) {
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
                    if (bottomNavigationView.getMenu()
                            .findItem(bottomNavigationView.getSelectedItemId()).title != "Асоси"
                    ) {
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


                }
                R.id.subjects -> {
                    if (bottomNavigationView.getMenu()
                            .findItem(bottomNavigationView.getSelectedItemId()).title != "Фанҳо"
                    ) {
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
                }
                R.id.classes -> {
                    if (bottomNavigationView.getMenu()
                            .findItem(bottomNavigationView.getSelectedItemId()).title != "Синфҳо"
                    ) {
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
                R.id.olympiad -> {
                    if (bottomNavigationView.getMenu()
                            .findItem(bottomNavigationView.getSelectedItemId()).title != "Олимпиада"
                    ) {
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.classes, true)
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.subjects, true)
                        findNavController(R.id.myNavHostFragment)
                            .navigate(
                                R.id.olympiad,
                                null,
                                NavOptions.Builder()
                                    .setPopUpTo(
                                        R.id.splashScreenFragment,
                                        true
                                    ).build()
                            )
                    }
//                    btnNotify()
                }
            }
            true
        }

    }

    private fun getSelectedItem(bottomNavigationView: BottomNavigationView): Int {
        val menu: Menu = bottomNavigationView.menu
        for (i in 0 until bottomNavigationView.menu.size()) {
            val menuItem: MenuItem = menu.getItem(i)
            if (menuItem.isChecked()) {
                return menuItem.getItemId()
            }
        }
        return 0
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "Pause", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "Resume", Toast.LENGTH_LONG).show()
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
