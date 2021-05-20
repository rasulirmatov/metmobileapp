package com.app.maktabielektroni

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var appSettingPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        appSettingPrefs = getSharedPreferences("AppSettingPrefs", Context.MODE_PRIVATE)
//        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        val isNotificationEnabled: Boolean = appSettingPrefs.getBoolean("ReceiveNotification", true)

        if (isNotificationEnabled) {
            FirebaseMessaging.getInstance().subscribeToTopic("Notification")
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("Notification")
        }


        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }

        Firebase.messaging.getToken().addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

        })

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
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.olympiad, true)
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.MMTFragment, true)
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
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.olympiad, true)
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.MMTFragment, true)
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
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.olympiad, true)
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.MMTFragment, true)
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
                R.id.MMTFragment -> {
                    if (bottomNavigationView.getMenu()
                            .findItem(bottomNavigationView.getSelectedItemId()).title != "ММТ"
                    ) {
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.olympiad, true)
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.MMTFragment, true)
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.classes, true)
                        findNavController(R.id.myNavHostFragment).popBackStack(R.id.subjects, true)
                        findNavController(R.id.myNavHostFragment)
                            .navigate(
                                R.id.MMTFragment,
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

    companion object {

        private const val TAG = "MainActivity"
    }

}
