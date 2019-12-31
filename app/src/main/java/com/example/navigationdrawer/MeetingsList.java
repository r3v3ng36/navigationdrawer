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

import com.example.navigationdrawer.Adapter.MeetingAdapter;
import com.example.navigationdrawer.Model.MeetingModal;
import com.example.navigationdrawer.Model.MeetingModal;
import com.example.navigationdrawer.interfaces.Api;
import com.example.navigationdrawer.interfaces.redditapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeetingsList extends AppCompatActivity {

    String BASE_URL = "https://step.focusspoint.com/api/";
    String USER;
    RecyclerView recyclerView;
    ImageView iv_back;
    private List<MeetingModal> MeetingModalList = new ArrayList<>();
    MeetingAdapter MeetingAdapter;
    private ProgressDialog progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_meetings_list);


        ServiceCall();
        recyclerView = findViewById(R.id.rv_attendence);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        MeetingAdapter = new MeetingAdapter(this, MeetingModalList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(MeetingAdapter);
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
        Call<List<MeetingModal>> call = api.getallmeeting();
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<List<MeetingModal>>() {
            @Override
            public void onResponse(Call<List<MeetingModal>> call, Response<List<MeetingModal>> response) {
                if(!response.isSuccessful()){

                    return;
                }
                else if (response.isSuccessful()){

                    List<MeetingModal> meetingList = response.body();

                    for (MeetingModal meeting: meetingList) {
                        int i = 0;
                        MeetingModalList.add( new MeetingModal(
                                meeting.getId(),
                                meeting.getTitle(),
                                meeting.getDate(),
                                meeting.getCheckIn(),
                                meeting.getCheckOut(),
                                meeting.getzMeetingId(),
                                meeting.getzMeetingPass(),
                                meeting.getSignature(),
                                meeting.getStatus()
                                ));
                        i = i++;

                        MeetingAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<MeetingModal>> call, Throwable t) {
                Log.d("Failure response : ",t.toString());

            }
        });
    }
}
