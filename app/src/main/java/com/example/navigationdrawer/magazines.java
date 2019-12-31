package com.example.navigationdrawer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.navigationdrawer.Adapter.MagzinesAdapter;
import com.example.navigationdrawer.Model.MagzineModel;
import com.example.navigationdrawer.interfaces.ClickListener;
import com.example.navigationdrawer.interfaces.redditapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class magazines extends AppCompatActivity implements ClickListener {

    RecyclerView recyclerView;
    MagzinesAdapter magzinesAdapter;
    List<MagzineModel> magzineModelList = new ArrayList<>();
    String BASE_URL = "https://step.focusspoint.com/api/";
    String USER;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_magazines);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        USER = prefs.getString("email","");
        ServiceCall();
        recyclerView = findViewById(R.id.rv_magzines);
        iv_back = findViewById(R.id.iv_back);
        magzinesAdapter = new MagzinesAdapter(this,this, magzineModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(magzinesAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),NavigationDrawer.class);
        startActivity(intent);
    }

    public void ServiceCall(){
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<MagzineModel>> call = ra.getmagazinefeed(USER);
        call.enqueue(new Callback<List<MagzineModel>>() {
            @Override
            public void onResponse(Call<List<MagzineModel>> call, Response<List<MagzineModel>> response) {
                if(!response.isSuccessful()){

                    return;
                }
                else if (response.isSuccessful()) {
                    List<MagzineModel> feeds = response.body();
                    if (feeds.get(0).getId().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "There is no media", Toast.LENGTH_LONG).show();

                    } else {

                        for (int i = 0; i < feeds.size(); i++) {
                            magzineModelList.add(new MagzineModel(feeds.get(i).getFileTitle(),
                                    feeds.get(i).getDate(),
                                    feeds.get(i).getCTitle(),
                                    feeds.get(i).getPath(),
                                    feeds.get(i).getCourseId(),
                                    feeds.get(i).getId(),
                                    feeds.get(i).getCId(),
                                    feeds.get(i).getFileLink()));
                            magzinesAdapter.notifyDataSetChanged();
                        }

                    }
                }


            }

            @Override
            public void onFailure(Call<List<MagzineModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "There is no media", Toast.LENGTH_LONG).show();
                Log.d("Failure response : ",t.toString());

            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onPositionClicked(int position) {

        if (magzineModelList.get(position).getFileLink().isEmpty()){
            Toast.makeText(getApplicationContext(),"There is no media file",Toast.LENGTH_LONG).show();


        }


        String Url = "https://step.focusspoint.com/"+ magzineModelList.get(position).getFileLink();
       // Toast.makeText(getApplicationContext(), Url,Toast.LENGTH_LONG).show();

        if (Url.contains("jpeg") || Url.contains("mp4")){

            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            prefEditor.putString("url", Url);
            prefEditor.apply();



            Intent intent = new Intent(magazines.this, videoview.class);
            startActivity(intent);
        }else if (Url.contains("pdf")){


//            Toast.makeText(this, Url, Toast.LENGTH_SHORT).show();
            Intent browserIntent = new Intent(getApplicationContext(), pdfview.class);
            browserIntent.putExtra("pdfurl",Url);
            startActivity(browserIntent);
        }


    }

}
