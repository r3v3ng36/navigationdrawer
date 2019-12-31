package com.example.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.splashactivity);
        deleteCache(this);

        Runnable spashrun = new Runnable() {
            @Override
            public void run() {
                loginactivity();
                finish();
            }
        };
        Handler myhandler = new Handler();
        myhandler.postDelayed(spashrun,3500);
    }
    public void loginactivity(){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");
        String role = prefs.getString("role", "");
//         Toast.makeText(getApplicationContext(),email+"   "+password +""+role, Toast.LENGTH_LONG).show();
        if (!email.isEmpty() && !password.isEmpty() && role.equals("3")){
            Intent intent = new Intent(this,NavigationDrawer.class);
            startActivity(intent);
        }
        else if(!email.isEmpty() && !password.isEmpty() && role.equals("1")){
            Intent intent = new Intent(this, task_management.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
