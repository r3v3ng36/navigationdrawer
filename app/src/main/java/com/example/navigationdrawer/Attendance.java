package com.example.navigationdrawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.navigationdrawer.Adapter.AttendenceAdapter;
import com.example.navigationdrawer.Model.AttendeceModel;
import com.example.navigationdrawer.interfaces.redditapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Attendance extends AppCompatActivity {

    String BASE_URL = "https://step.focusspoint.com/api/";
    String USER;
    RecyclerView recyclerView;
    ImageView iv_back;
    private List<AttendeceModel> attendeceModelList = new ArrayList<>();
    AttendenceAdapter attendenceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_attendance);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        USER = prefs.getString("email","");
        ServiceCall();
        recyclerView = findViewById(R.id.rv_attendence);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        attendenceAdapter = new AttendenceAdapter(this, attendeceModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(attendenceAdapter);
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
        redditapi ra = rf.create(redditapi.class);
        Call<List<AttendeceModel>> call = ra.getattendancefeed(USER);
        call.enqueue(new Callback<List<AttendeceModel>>() {
            @Override
            public void onResponse(Call<List<AttendeceModel>> call, Response<List<AttendeceModel>> response) {
                if(!response.isSuccessful()){

                    return;
                }
                else if (response.isSuccessful()){
                    List<AttendeceModel> feeds = response.body();


                    for (int i = 0; i < feeds.size(); i++) {
                        int sno = 0;
                        String no = String.valueOf(sno);
                        attendeceModelList.add(new AttendeceModel(
                                no,
                                feeds.get(i).getSaStdNo(),
                               "Date "+ "\n" +feeds.get(i).getSaDate(),
                                feeds.get(i).getSaOntime(),
                                feeds.get(i).getSaOfftime(),
                                feeds.get(i).getSaCheckin(),
                                feeds.get(i).getSaCheckout(),
                                feeds.get(i).getSaLate(),
                                feeds.get(i).getSaEarly(),
                                "Status"+ "\n"+feeds.get(i).getSaAbsent(),
                                feeds.get(i).getSaTotaltime(),
                                feeds.get(i).getSaCourseid(),
                                feeds.get(i).getSaExtraClassid()));
                        attendenceAdapter.notifyDataSetChanged();
                        sno = sno + 1;
                    }
                }


            }

            @Override
            public void onFailure(Call<List<AttendeceModel>> call, Throwable t) {
                Log.d("Failure response : ",t.toString());

            }
        });
    }
}
