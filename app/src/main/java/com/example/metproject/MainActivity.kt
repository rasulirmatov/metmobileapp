package com.example.metproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
//        window.statusBarColor = ContextCompat.getColor(this,R.color.white);// set status background white
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility = View.GONE
            bottomNavigationView.menu.forEach {
                if (it.itemId == destination.id || destination.id == R.id.mainFragment) {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }
}
