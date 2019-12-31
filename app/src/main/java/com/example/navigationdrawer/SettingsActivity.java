package com.example.navigationdrawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsActivity extends AppCompatActivity {

    ImageView iv_back;
    Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_settings);
        iv_back = findViewById(R.id.iv_back);
        switch1 = findViewById(R.id.switch1);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                if (isChecked == true){
                    editor.putBoolean("NOTIFICTION",true);
                    editor.apply();
                    switch1.setChecked(true);
                }else {
                    editor.putBoolean("NOTIFICTION",false);
                    editor.apply();
                    switch1.setChecked(false);
                   //    FirebaseMessaging.getInstance().subscribeToTopic("news");
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean NOTIFICTION = prefs.getBoolean("NOTIFICTION",false);
        if(NOTIFICTION == true){
            switch1.setChecked(true);

        }
    }
}
