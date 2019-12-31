package com.example.navigationdrawer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.navigationdrawer.Adapter.DaysAdapter;
import com.example.navigationdrawer.Adapter.NoticesAdapter;
import com.example.navigationdrawer.Model.DaysModel;
import com.example.navigationdrawer.Model.FeedModel;
import com.example.navigationdrawer.Model.noticefeedModel;
import com.example.navigationdrawer.interfaces.redditapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AccountFragment extends Fragment {

    TextView et_email,qualification,gender;
    TextView txt_std_id,txt_user_name;
    CircleImageView profile_image;
    private List<DaysModel> daysModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    DaysAdapter daysAdapter;
    View view;
    String BASE_URL = "https://step.focusspoint.com/api/";
    String USER;
    private ProgressDialog progressBar;

    @Override
    public void onResume() {
        super.onResume();
        ServiceCall();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void ServiceCall() {
        progressBar = ProgressDialog.show(getContext(), "", "Loading...");

        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<FeedModel>> call = ra.getfeed(USER);
        call.enqueue(new Callback<List<FeedModel>>() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onResponse(Call<List<FeedModel>> call, Response<List<FeedModel>> response) {
                if (!response.isSuccessful()) {
                    //tv.setText("Code : " + response.code());
                    return;
                } else if (response.isSuccessful()) {
                    List<FeedModel> feeds = response.body();
                    for (FeedModel feed : feeds) {
                        String email = feed.getS_email();
                        String str_days = feed.getCd_days();
                        String std_id = feed.getS_idno();
                        String str_gender = feed.getS_gender();
                        String username = feed.getS_fname();
                        String lastname = feed.getS_lname();
                        String strqualification = feed.getS_last_qualification();
                        String str_checkin = feed.getCd_checkin();
                        String str_checkout = feed.getCd_checkout();
                        String coursename = feed.getC_title();
                        String userimage = feed.getS_photo();
                        String course_date = feed.getS_createby();
                        String url = "https://step.focusspoint.com/images/students/" + userimage;
                        et_email.setText(email);
                        txt_user_name.setText(username + " " + lastname);
                        txt_std_id.setText(std_id);
                        qualification.setText(strqualification);

                        ServiceCallDays(feed.getS_course_id());
                        gender.setText(str_gender);

                        Glide.with(getContext()).load(url).placeholder(R.drawable.onimage).into(profile_image);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<FeedModel>> call, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                //Log.d("Failure response : ",tv.toString());
                //tv.setText(t.getMessage());
            }
        });
    }



    private void ServiceCallDays(String course) {

        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<DaysModel>> call = ra.course(course);
        call.enqueue(new Callback<List<DaysModel>>() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onResponse(Call<List<DaysModel>> call, Response<List<DaysModel>> response) {
                progressBar.dismiss();
                if (!response.isSuccessful()) {
                    //tv.setText("Code : " + response.code());
                    return;
                } else if (response.isSuccessful()) {
                    List<DaysModel> feeds = response.body();


                        for (int i = 0; i < feeds.size(); i++) {
                            daysModelList.add(new DaysModel(
                                    feeds.get(i).getCdDays(),feeds.get(i).getCdCheckin(),feeds.get(i).getCdCheckout()));
                            daysAdapter.notifyDataSetChanged();
                        }



                }
            }
            @Override
            public void onFailure(Call<List<DaysModel>> call, Throwable t) { progressBar.dismiss();

                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                //Log.d("Failure response : ",tv.toString());
                //tv.setText(t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.accountfragment, container, false);
        initViews();

        daysAdapter = new DaysAdapter(getContext(), daysModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(daysAdapter);


        return view;
    }

    private void initViews() {
        recyclerView = view.findViewById(R.id.rv_days);
        et_email = view.findViewById(R.id.email);
        profile_image = view.findViewById(R.id.profile_image);
        qualification = view.findViewById(R.id.qualification);
        gender = view.findViewById(R.id.gender);
        txt_std_id = view.findViewById(R.id.txt_std_id);
        txt_user_name = view.findViewById(R.id.User_name);
    }
}
