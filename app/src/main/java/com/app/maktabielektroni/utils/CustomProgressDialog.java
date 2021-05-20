package com.app.maktabielektroni.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;


import com.app.maktabielektroni.R;

import androidx.annotation.NonNull;

public class CustomProgressDialog extends Dialog {

    private final ViewHolder viewHolder;
    public CustomProgressDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View shareDoctorView = getLayoutInflater().inflate(R.layout.custom_progress, null);
        setContentView(shareDoctorView);
        viewHolder = new ViewHolder(shareDoctorView);

    }
    public void show(String msg){
        viewHolder.tvProgressMsg.setText(msg);
        show();
    }
    class ViewHolder {
        TextView tvProgressMsg;
        ViewHolder(View view) {
            tvProgressMsg=view.findViewById(R.id.tv_progress_msg);
        }
    }
}

