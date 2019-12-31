package com.example.navigationdrawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.navigationdrawer.Adapter.NotificationAdapter;
import com.example.navigationdrawer.Model.notificationfeedModel;
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


public class NotificationFragment extends Fragment {

    View view;
    private List<notificationfeedModel> notificationfeedModelList ;
    private RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    String BASE_URL = "https://step.focusspoint.com/api/";
    String USER;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationfeedModelList = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        USER = prefs.getString("email","");

        String uid = prefs.getString("uid", "");
        CheckIfLogin(USER,uid);
    }

    private void CheckIfLogin(String myuser, String device_id) {

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
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("email");
                        editor.remove("password");
                        editor.putString("attendeceCount","0");
                        editor.putString("magzineCount","0");
                        editor.putString("upcomingCount","0");
                        editor.putString("noticesCount","0");

                        editor.clear();
                        editor.commit();
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

    @Override
    public void onResume() {
        super.onResume();
        serviceCall();
    }

    private void serviceCall() {
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<notificationfeedModel>> call = ra.getnotifeed(USER);
        call.enqueue(new Callback<List<notificationfeedModel>>() {
            @Override
            public void onResponse(Call<List<notificationfeedModel>> call, Response<List<notificationfeedModel>> response) {
                if(!response.isSuccessful()){

                    return;
                }
                else if (response.isSuccessful()){
                    List<notificationfeedModel> feeds = response.body();
                    for (int i = 0; i < feeds.size(); i++) {
                        notificationfeedModelList.add(new notificationfeedModel(
                                feeds.get(i).getN_message(),
                                feeds.get(i).getN_time()));
                        notificationAdapter.notifyDataSetChanged();
                    }
                }


            }

            @Override
            public void onFailure(Call<List<notificationfeedModel>> call, Throwable t) {
                Log.d("Failure response : ",t.toString());
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.notificationfragment, container, false);
        initViews();
        notificationAdapter = new NotificationAdapter(getContext(), notificationfeedModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(notificationAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    private void initViews() {
        recyclerView = view.findViewById(R.id.rv_notification);
    }
}
