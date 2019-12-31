package com.example.navigationdrawer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    String BASE_URL = "https://step.focusspoint.com/api/";
    String USER = "";
    String Roles = "";
    SharedPreferences sharedPreferences;
    String token = "";
    BottomNavigationView bottomNav;
    Button mybtn;
    String device_id;

    private static final String TAG = NavigationDrawer.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_navigation_drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        USER = preferences.getString("email", "");
        Roles = preferences.getString("","");
        device_id = preferences.getString("uid",device_id);


       // notificationfire();

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("MYNotificaction","MYNotificaction",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



//        Bottomnav
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_favorites);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //serviceCall();
                handler.postDelayed(this, 20000);
            }
        }, 20000);  //the time is in miliseconds

        token = FirebaseInstanceId.getInstance().getToken();
        Log.e("token","Your Token"+token);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TOKEN",token);
        editor.commit();
      //  Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();

    }



    @Override
    protected void onResume() {
        super.onResume();
//        Intent myintent = new Intent(NavigationDrawer.this,LoginActivity.class);
//        startActivity(myintent);

       Fragment selectedFragment = new NoticesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
        bottomNav.setSelectedItemId(R.id.nav_favorites);
        toolbar.setTitle("Notices");
      //  serviceCall();

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }




    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            fragment = new HomeFragment();
                            loadFragment(fragment);
                            toolbar.setTitle("Upcoming Courses");
                            break;
                        case R.id.nav_favorites:
                            fragment = new NoticesFragment();
                            loadFragment(fragment);
                            toolbar.setTitle("Notices");
                            break;
                        case R.id.nav_search:
                            fragment = new NotificationFragment();
                            loadFragment(fragment);
                            toolbar.setTitle("Notifications");
                            break;
                        case R.id.nav_custom:
                            fragment = new AccountFragment();
                            loadFragment(fragment);
                            toolbar.setTitle("Account");
                            break;


                    }



                    return true;
                }
            };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            finish();
        }
    }
    public void showcourse(){
        Intent myintent = new Intent(this,coursedetail.class);
        startActivity(myintent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myintent = new Intent(this,SettingsActivity.class);
            startActivity(myintent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_attendance) {
            Intent myintent = new Intent(this,Attendance.class);
            startActivity(myintent);

        } else if (id == R.id.nav_magazines) {
            Intent myintent = new Intent(this,magazines.class);
            startActivity(myintent);
        } else if (id == R.id.nav_support) {
            Intent myintent = new Intent(this,Support.class);
            startActivity(myintent);
        } else if (id == R.id.nav_rules) {
            Intent myintent = new Intent(this,Rulesandregulation.class);
            startActivity(myintent);
        }
        else if (id == R.id.nav_registration_invoice) {
            Intent myintent = new Intent(this,RegisterInvoice.class);
            startActivity(myintent);

        }
        else if (id == R.id.nav_settings) {
            Intent myintent = new Intent(this,SettingsActivity.class);
            startActivity(myintent);
        }

        else if (id==R.id.nav_onlinesession){
            Intent intent = new Intent(this,OnlineSession.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_logout) {



            logout();
//            finish();
//            Intent myintent = new Intent(getApplicationContext(),LoginActivity.class);
//            startActivity(myintent);



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void notificationfire(String Title,String Content){
        Intent intent = new Intent(NavigationDrawer.this, NavigationDrawer.class);
        PendingIntent contentIntent = PendingIntent.getActivity(NavigationDrawer.this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder b = new NotificationCompat.Builder(NavigationDrawer.this);
        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.splashscreen)
                .setTicker("Mayank")
                .setContentTitle(Title)
                .setContentText(Content)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent) .setContentInfo("Info");
        NotificationManager notificationManager = (NotificationManager) NavigationDrawer.this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }

    public  void logout(){

        Call<ResponseBody> call = APIClient
                .getInstance()
                .getApi()
                .logout(USER,"",device_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //avi.hide();
                    JSONObject mainObject = new JSONObject(response.body().string());

                    Integer  status = Integer.valueOf(mainObject.optString("status"));
                    String  value = mainObject.optString("value");


                    if (status == 200){
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("email");
                        editor.remove("password");
                        editor.putString("attendeceCount","0");
                        editor.putString("magzineCount","0");
                        editor.putString("upcomingCount","0");
                        editor.putString("noticesCount","0");

                        editor.clear();
                        editor.commit();


                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        finish();
//                        Intent myintent = new Intent(getApplicationContext(),LoginActivity.class);
//                        startActivity(myintent);
                    }else  if (status == 500){
                          Toast.makeText(NavigationDrawer.this,"Check Internet Connection.",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(NavigationDrawer.this,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(NavigationDrawer.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(NavigationDrawer.this, t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
}
