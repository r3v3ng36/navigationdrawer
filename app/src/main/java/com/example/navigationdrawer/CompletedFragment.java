package com.example.navigationdrawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Adapter.CompletedtaskAdapter;
import com.example.navigationdrawer.Model.CompletedTaskModel;
import com.example.navigationdrawer.interfaces.ClickListener;
import com.example.navigationdrawer.interfaces.redditapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompletedFragment extends Fragment {
    View view;
    private List<CompletedTaskModel> completedTaskModels;
    private RecyclerView recyclerView;
    CompletedtaskAdapter cta;
    String BASE_URL = "https://step.focusspoint.com/api/completed-task/";
    String USER = "";
    Button mybtn;
    String myuser = "";
    String  device_id ="";
    TextView tv_employee,tv_status,tv_recording,tv_assign_date,tv_completion_date,tv_task_detail;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        completedTaskModels = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        USER = preferences.getString("email", "");

        String uid = preferences.getString("uid", "");
        CheckIfLogin(USER,uid);
        serviceCall();




    }

    private void CheckIfLogin(String myuser, String  device_id) {

        Call<ResponseBody> call = APIClient
                .getInstance()
                .getApi()
                .checkLogin(myuser,device_id);
        //Toast.makeText(getApplicationContext(), FirebaseInstanceId.getInstance().getToken(),Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //avi.hide();
                    JSONObject mainObject = new JSONObject(response.body().string());

                    Integer  status = Integer.valueOf(mainObject.optString("status"));
                    String  value = mainObject.optString("value");
                    String User = mainObject.optString("user");
                    Integer role = mainObject.optInt("role");


                    if (status == 500){
                        Toast.makeText(getContext(), "Sorry, you have been logged out..", Toast.LENGTH_SHORT).show();
                        Intent myintent = new Intent(getContext(),LoginActivity.class);
                        startActivity(myintent);

                    }else  if (status == 200){
//                        Toast.makeText(getContext(), "Is Login", Toast.LENGTH_SHORT).show();
                        return;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.completedfragment, container, false);



        recyclerView = view.findViewById(R.id.rv_completed_task);

        tv_employee = view.findViewById(R.id.tv_employee);
        tv_status = view.findViewById(R.id.tv_status);
        tv_recording = view.findViewById(R.id.tv_recording);
        tv_assign_date= view.findViewById(R.id.tv_assign_date);
        tv_completion_date= view.findViewById(R.id.tv_completion_date);
        tv_task_detail = view.findViewById(R.id.tv_task_detail);


        mybtn = view.findViewById(R.id.mbtn);
        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getContext(),create_task.class);
                startActivity(myintent);
            }
        });
        cta = new CompletedtaskAdapter(getContext(), completedTaskModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(cta);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
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
        Call<List<CompletedTaskModel>> call = ra.getcompletedtask();
        call.enqueue(new Callback<List<CompletedTaskModel>>() {
            @Override
            public void onResponse(Call<List<CompletedTaskModel>> call, Response<List<CompletedTaskModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                } else if (response.isSuccessful()) {
                    List<CompletedTaskModel> feeds = response.body();
                    if (!feeds.get(0).getStatus().isEmpty()) {
                        for (int i = 0; i < feeds.size(); i++) {
                            completedTaskModels.add(new CompletedTaskModel(
                                    feeds.get(i).getEmployee(),
                                    feeds.get(i).getTask_detail(),
                                    feeds.get(i).getRecording(),
                                    feeds.get(i).getAssign_date(),
                                    feeds.get(i).getCompletion_date(),
                                    feeds.get(i).getStatus()));
                            cta.notifyDataSetChanged();
                        }
                    }
                }else{
                    Toast.makeText(getContext(),"There is no completed task",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<List<CompletedTaskModel>> call, Throwable t) {
                Log.d("Failure response : ", t.toString());
                Toast.makeText(getContext(),"There is no Completed Task",Toast.LENGTH_SHORT).show();
            }
        });


    }

}
