package com.example.metproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
//        window.statusBarColor = ContextCompat.getColor(this,R.color.white);// set status background white
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}