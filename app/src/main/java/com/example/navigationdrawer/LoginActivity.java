package com.example.navigationdrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    Button btn_login, btn_visitor;
    EditText et_mail, et_password;
    String Username;
    ImageView ivWhatsapp, ivPhone, iv_email, ivWhatsapp2, facbook, iv_youtube;
    TextView tv_upcoming;
    Context context;
    public static String FACEBOOK_URL = "https://www.facebook.com/focusspoint";
    public static String FACEBOOK_PAGE_ID = "209398962516749";
    private ProgressDialog progressBar;
    String email;
    String password;
    String roles;
    String uid,Token;
    TextView tv_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_login);
        initViews();
        btn_visitor.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_upcoming.setOnClickListener(this);
        ivWhatsapp.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
        ivWhatsapp2.setOnClickListener(this);
        iv_email.setOnClickListener(this);
        facbook.setOnClickListener(this);
        iv_youtube.setOnClickListener(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Token = prefs.getString("TOKEN", "123c");
//        String ts = Context.TELEPHONY_SERVICE;
//        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(ts);
//        String imei = mTelephonyMgr.getDeviceId();
        uid = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        tv_notification.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notifcation = new Intent(LoginActivity.this,all_notifcations.class);
                startActivity(notifcation);
            }
        });
      //  Toast.makeText(this,m_deviceId,Toast.LENGTH_LONG).show();

    }
    public void initViews(){
        ivWhatsapp = findViewById(R.id.iv_whatsapp);
        btn_visitor = findViewById(R.id.visitor);
        btn_login = findViewById(R.id.btn_login);
        et_mail = findViewById(R.id.editText2);
        et_password = findViewById(R.id.editText3);
        tv_upcoming = findViewById(R.id.tv_upcoming);
        ivPhone = findViewById(R.id.iv_phone);
        ivWhatsapp2 = findViewById(R.id.iv_whatsapp2);
        iv_email = findViewById(R.id.iv_email);
        facbook = findViewById(R.id.facbook);
        iv_youtube = findViewById(R.id.iv_youtube);
        tv_notification = findViewById(R.id.tv_notification);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = prefs.getString("email", "");
        password = prefs.getString("password", "");
        roles = prefs.getString("roles", "");
       // Toast.makeText(getApplicationContext(),email+"   "+password,Toast.LENGTH_LONG).show();
        if (email.equals(null) && password.equals(null) && roles.equals(null)){
            loginCall();
        }
        else{
            return;
        }

    }



    public  void loginCall(){
        progressBar = ProgressDialog.show(LoginActivity.this, "", "Loading...");
        Username = et_mail.getText().toString();
        String Password = et_password.getText().toString();
        String Login = "";
        Integer role = null;
        String FCMtoken = FirebaseInstanceId.getInstance().getToken();


        Call<ResponseBody> call = APIClient
                .getInstance()
                .getApi()
                .createUser(Username,Password,uid,"android",FCMtoken,Login,role);
        //Toast.makeText(getApplicationContext(), FirebaseInstanceId.getInstance().getToken(),Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.dismiss();
                try {
                    //avi.hide();
                    JSONObject mainObject = new JSONObject(response.body().string());

                    Integer  status = Integer.valueOf(mainObject.optString("status"));
                    String  value = mainObject.optString("value");
                   String User = mainObject.optString("user");
                    Integer role = mainObject.optInt("role");
                    Log.d("Role ",String.valueOf(role));
//                    Toast.makeText(getApplicationContext(),"Roles" + role,Toast.LENGTH_SHORT).show();

                    //  Log.e("response",status);
                    //getData();


//                    Toast.makeText(LoginActivity.this,"This is the id to passed"+ User, Toast.LENGTH_SHORT).show();


                    if (status == 200 && role == 3){

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("USER",User);
                        editor.putString("UID",uid);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this,NavigationDrawer.class);
                        intent.putExtra("Username", Username);
                        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                        prefEditor.clear();
                        prefEditor.putString("email", et_mail.getText().toString());
                        prefEditor.putString("password", et_password.getText().toString());
                        prefEditor.putString("role", role.toString());
                        prefEditor.putString("uid", uid);
//                        Toast.makeText(getApplicationContext(),"Student :" + et_mail.getText().toString() + et_password.getText().toString() + role.toString(),Toast.LENGTH_SHORT).show();
                        prefEditor.apply();
                        editor.putBoolean("LOGIN",true);
                        editor.apply();
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this,value,Toast.LENGTH_LONG).show();
                    }
                    else if (status == 200 && role == 1){

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("USER",User);
                        editor.putString("UID",uid);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this,task_management.class);
                        intent.putExtra("Username", Username);
                        intent.putExtra("device_id",uid);
                        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                        prefEditor.clear();
                        prefEditor.putString("email", et_mail.getText().toString());
                        prefEditor.putString("password", et_password.getText().toString());
                        prefEditor.putString("role", role.toString());
                        prefEditor.putString("uid", uid);
//                        Toast.makeText(getApplicationContext(),"Admin :" + et_mail + et_password + role,Toast.LENGTH_SHORT).show();
                        prefEditor.apply();
                        editor.putBoolean("LOGIN",true);
                        editor.apply();
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this,value,Toast.LENGTH_LONG).show();
                    }
                    else  if (status == 500){
                        Toast.makeText(LoginActivity.this,value,Toast.LENGTH_LONG).show();
                    }else  if (status == 302){
                        Toast.makeText(LoginActivity.this,value,Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

//    facbook.com/focusspoint
    // youtube.com/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            SeriveCall();

        } else  if (v.getId() == R.id.tv_upcoming){
            Intent intent = new Intent(LoginActivity.this,UpComingCoursesPopup.class);
            startActivity(intent);
        } else if (v.getId() == R.id.iv_whatsapp){
//            String toNumber = "+923058222528"; // contains spaces.
//            toNumber = toNumber.replace("+", "").replace(" ", "");
//            Intent sendIntent = new Intent("android.intent.action.MAIN");
//            sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "I have a query.");
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.setPackage("com.whatsapp");
//            sendIntent.setType("text/plain");
//            startActivity(sendIntent);
            try {
                String text = "I have a query.";// Replace with your message.
                String toNumber = "+923058222528";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                startActivity(intent);
            }
            catch (Exception e){
                e.printStackTrace();
            }




        }else  if (v.getId() ==  R.id.iv_phone){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:03008222528"));
            startActivity(intent);
        }else  if (v.getId() ==  R.id.iv_whatsapp2){
            try {
                String text = "I have a query.";// Replace with your message.
                String toNumber = "+923008222528";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                startActivity(intent);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else  if (v.getId() ==  R.id.iv_email){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"admin@focusspoint.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "");
            i.putExtra(Intent.EXTRA_TEXT   , "");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        } else  if (v.getId() ==  R.id.iv_youtube){

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/user/focusspoint/"));
            intent.setPackage("com.google.android.youtube");
            startActivity(intent);
        }else if (v.getId() == R.id.facbook){

            Intent facebookintent = getOpenFacebookIntent(LoginActivity.this);
            startActivity(facebookintent);

        }else if (v.getId() == R.id.visitor){

            Intent meetingIntent = new Intent(this,MeetingsList.class);
            startActivity(meetingIntent);

        }
    }


    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/209398962516749"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/focusspoint"));
        }
    }



    private void SeriveCall(){
        email = et_mail.getText().toString();
        password = et_password.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Plesse enter your email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Plesse enter your password", Toast.LENGTH_SHORT).show();
        } else {

            loginCall();
        }
    }


}

