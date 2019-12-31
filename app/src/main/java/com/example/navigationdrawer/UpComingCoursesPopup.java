package com.example.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.navigationdrawer.Adapter.CourseAdapter;
import com.example.navigationdrawer.Model.CourseFeedModel;
import com.example.navigationdrawer.interfaces.ClickListener;
import com.example.navigationdrawer.interfaces.redditapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UpComingCoursesPopup extends AppCompatActivity implements ClickListener{

    View view;
    private List<CourseFeedModel> courseFeedModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    CourseAdapter courseAdapter;
    String BASE_URL = "https://step.focusspoint.com/api/";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.upcoming_courses_popup);
        initViews();
        courseAdapter = new CourseAdapter(this,this,courseFeedModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseAdapter);
        serviceCall();

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void initViews(){
        recyclerView = findViewById(R.id.rv_upcoming);

    }

    public void serviceCall(){

        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<CourseFeedModel>> call = ra.getcoursefeed();
        call.enqueue(new Callback<List<CourseFeedModel>>() {
            @Override
            public void onResponse(Call<List<CourseFeedModel>> call, Response<List<CourseFeedModel>> response) {

                courseFeedModelList.clear();

                if(!response.isSuccessful()){
                    return;
                }
                else if (response.isSuccessful()) {
                    List<CourseFeedModel> feeds = response.body();
                    for (int i = 0; i < feeds.size(); i++) {
                        String course = feeds.get(i).getCUpcoming();

                        if (course.matches("1")) {
                            courseFeedModelList.add(new CourseFeedModel(
                                    feeds.get(i).getCId(),
                                    feeds.get(i).getCEntryNo(),
                                    feeds.get(i).getCTitle(),
                                    feeds.get(i).getCFees(),
                                    feeds.get(i).getCDuration()));
                            courseAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CourseFeedModel>> call, Throwable t) {
                Log.d("Failure response : ",t.toString());
                Toast.makeText(UpComingCoursesPopup.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onPositionClicked(int position) {
        String id = courseFeedModelList.get(position).getCId();

        Intent intent = new Intent(this,coursedetail.class);
        intent.putExtra("id",id);
        startActivity(intent);

    }
}
