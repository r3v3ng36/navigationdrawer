package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.navigationdrawer.Adapter.OnlineSessionAdapter;
import com.example.navigationdrawer.Adapter.OnlineSessionAdapter;
import com.example.navigationdrawer.Model.OnlineSessionModal;
import com.example.navigationdrawer.Model.OnlineSessionModal;
import com.example.navigationdrawer.interfaces.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OnlineSession extends AppCompatActivity {

    String BASE_URL = "https://step.focusspoint.com/api/";
    String USER;
    RecyclerView recyclerView;
    ImageView iv_back;
    private List<OnlineSessionModal> OnlineSessionModalList = new ArrayList<>();
    OnlineSessionAdapter OnlineSessionAdapter;
    private ProgressDialog progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_online_session);



        ServiceCall();
        recyclerView = findViewById(R.id.onlinesession_recyclerview);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        OnlineSessionAdapter = new OnlineSessionAdapter(this, OnlineSessionModalList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(OnlineSessionAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void ServiceCall(){
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = rf.create(Api.class);
        Call<List<OnlineSessionModal>> call = api.getallonlinesession();
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<List<OnlineSessionModal>>() {
            @Override
            public void onResponse(Call<List<OnlineSessionModal>> call, Response<List<OnlineSessionModal>> response) {
                if(!response.isSuccessful()){

                    return;
                }
                else if (response.isSuccessful()){

                    List<OnlineSessionModal> onlinesessionList = response.body();

                    for (OnlineSessionModal onlinesession: onlinesessionList) {
                        int i = 0;
                        OnlineSessionModalList.add( new OnlineSessionModal(
                                onlinesession.getId(),
                                onlinesession.getTitle(),
                                onlinesession.getDate(),
                                onlinesession.getCheckIn(),
                                onlinesession.getCheckOut(),
                                onlinesession.getzMeetingId(),
                                onlinesession.getzMeetingPass(),
                                onlinesession.getSignature(),
                                onlinesession.getStatus(),
                                onlinesession.getVideoLink(),
                                onlinesession.isVistor(),
                                onlinesession.getCourseId()
                        ));
                        i = i++;

                        OnlineSessionAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<OnlineSessionModal>> call, Throwable t) {
                Log.d("Failure response : ",t.toString());

            }
        });
    }
}

