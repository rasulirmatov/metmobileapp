package com.app.maktabielektroni;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.maktabielektroni.utils.Constants;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about_app);

        TextView txtVersion = findViewById(R.id.versionName);
        TextView txtLink = findViewById(R.id.tvLink);
        txtLink.setOnClickListener(v -> {
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(Constants.website_url));
            startActivity(viewIntent);
        });

        try {
            txtVersion.setText(String.format("%s%s", "V", getPackageManager().getPackageInfo(getPackageName(), 0).versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
