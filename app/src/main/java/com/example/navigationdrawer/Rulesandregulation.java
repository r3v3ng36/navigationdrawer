package com.example.navigationdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.navigationdrawer.Model.MagzineModel;
import com.example.navigationdrawer.Model.RulesModel;
import com.example.navigationdrawer.interfaces.redditapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Rulesandregulation extends AppCompatActivity {

    WebView webView;
    ImageView iv_back;
    String BASE_URL = "https://step.focusspoint.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_rulesandregulation);
        webView = findViewById(R.id.webview);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ServiceCall();

//

    }


    public void ServiceCall(){
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<RulesModel>> call = ra.rules();
        call.enqueue(new Callback<List<RulesModel>>() {
            @Override
            public void onResponse(Call<List<RulesModel>> call, Response<List<RulesModel>> response) {
                if(!response.isSuccessful()){

                    return;
                }
                else if (response.isSuccessful()) {
                    List<RulesModel> feeds = response.body();

                    String yourData = feeds.get(0).getRules();

                   webView.loadData(yourData, "text/html", "UTF-8");

                }


            }

            @Override
            public void onFailure(Call<List<RulesModel>> call, Throwable t) {

                Log.d("Failure response : ",t.toString());

            }
        });
    }

}
