package com.example.navigationdrawer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdrawer.Model.coursedetailfeedModel;
import com.example.navigationdrawer.interfaces.redditapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class coursedetail extends AppCompatActivity {

    String str_id;
    Bundle bundle;
    String BASE_URL = "https://step.focusspoint.com/api/";
    TextView tv_course_detail,tv_teacher,tv_duration,tv_fees,tv_check;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_coursedetail);
        initViews();

        getSupportActionBar().setTitle(Html.fromHtml("<font text-align='center' color='#ffffff'> Course Name</font>"));
        bundle = getIntent().getExtras();
        str_id = bundle.getString("id");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Toast.makeText(coursedetail.this,str_id,Toast.LENGTH_SHORT).show();

    }

    public void initViews(){
        tv_course_detail = findViewById(R.id.tv_course_detail);
        tv_teacher = findViewById(R.id.tv_teacher);
        tv_duration = findViewById(R.id.tv_duration);
        tv_fees = findViewById(R.id.tv_fees);
        tv_check = findViewById(R.id.tv_check);
        iv_back = findViewById(R.id.iv_back);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ServiceCall();
    }

    public void ServiceCall(){

        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<coursedetailfeedModel>> call;
        call = ra.getcoursedetail(str_id);
        call.enqueue(new Callback<List<coursedetailfeedModel>>() {
            @Override
            public void onResponse(Call<List<coursedetailfeedModel>> call, Response<List<coursedetailfeedModel>> response) {
                if(!response.isSuccessful()){
                  //  tv.setText("Code : " + response.code());
                    return;
                }
                else if (response.isSuccessful()){
                    List<coursedetailfeedModel> feeds = response.body();
                    for (coursedetailfeedModel coursedetailfeed: feeds){
                        String content = "";
                        tv_course_detail.setText(coursedetailfeed.getC_title());
                        tv_duration.setText(coursedetailfeed.getC_duration());
                        tv_fees.setText(coursedetailfeed.getC_fees());
                        tv_teacher.setText(coursedetailfeed.getT_name());
                        //content += "ID : " + coursedetailfeed.getC_id() + "\n";
                        //course_id.setText(coursedetailfeed.getC_id().toString());
                        //content += "Teachers Name : " + coursedetailfeed.getT_name() + "\n";
                     //   teacher_name.setText(coursedetailfeed.getT_name());
                        //content += "Title : " + coursedetailfeed.getC_title() + "\n";
                     //   duration.setText(coursedetailfeed.getC_duration());
                        //content += "Duration : " + coursedetailfeed.getC_duration() + "\n";
                       // fees.setText(coursedetailfeed.getC_fees());

                        //content += "Fees : " + coursedetailfeed.getC_fees() + "\n";


//                        content += "USER ID : " + feed.getUserId() + "\n";
//                        content += "ID : " + feed.getId() + "\n";
//                        content += "TITLE : " + feed.getTitle() + "\n";
//                        content += "Text : " + feed.getText() + "\n";
                        //tv.append(content);
                    }
                }


            }

            @Override
            public void onFailure(Call<List<coursedetailfeedModel>> call, Throwable t) {
                Log.d("Failure response : ",t.toString());
              Toast.makeText(coursedetail.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
