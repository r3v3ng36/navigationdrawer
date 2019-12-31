package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdrawer.Adapter.AllNotiAdapter;
import com.example.navigationdrawer.Adapter.NoticesAdapter;
import com.example.navigationdrawer.Adapter.NotificationAdapter;
import com.example.navigationdrawer.Model.AllNotiModel;
import com.example.navigationdrawer.Model.MagzineModel;
import com.example.navigationdrawer.Model.noticefeedModel;
import com.example.navigationdrawer.interfaces.redditapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class all_notifcations extends AppCompatActivity {
    private List<AllNotiModel> noticefeedModelList ;
    private RecyclerView recyclerView;
    AllNotiAdapter noticesAdapter;
    String BASE_URL = "https://step.focusspoint.com/api/global-notices/";
    String USER = "";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_all_notifcations);
//        tv = findViewById(R.id.tv_employee);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(all_notifcations.this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
        noticefeedModelList = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        USER = preferences.getString("USER", "");
        initViews();
        noticesAdapter = new AllNotiAdapter(getApplicationContext(), noticefeedModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noticesAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        serviceCall();

    }
    @Override
    public void onResume() {
        super.onResume();

    }
    private void serviceCall() {

        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<AllNotiModel>> call = ra.getallnoticefeed();
        call.enqueue(new Callback<List<AllNotiModel>>() {
            @Override
            public void onResponse(Call<List<AllNotiModel>> call, Response<List<AllNotiModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                } else if (response.isSuccessful()) {
                    List<AllNotiModel> feeds = response.body();
                    if (!feeds.get(0).getNotices().isEmpty()) {
                        for (int i = 0; i < feeds.size(); i++) {
                            noticefeedModelList.add(new AllNotiModel(
                                    feeds.get(i).getNotices()));
                            noticesAdapter.notifyDataSetChanged();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"There is no notices",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<List<AllNotiModel>> call, Throwable t) {
                Log.d("Failure response : ", t.toString());
                Toast.makeText(getApplicationContext(),"There is no notices",Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void initViews() {
        recyclerView = findViewById(R.id.rv_all_notification);
    }



}
