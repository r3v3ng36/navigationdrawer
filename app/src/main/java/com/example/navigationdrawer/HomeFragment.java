package com.example.navigationdrawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationdrawer.Adapter.CourseAdapter;
import com.example.navigationdrawer.Model.CourseFeedModel;
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


public class HomeFragment extends Fragment implements ClickListener {

    View view;
    private List<CourseFeedModel> courseFeedModelList = new ArrayList<>() ;
    private RecyclerView recyclerView;
    CourseAdapter courseAdapter;
    EditText et_search;
    String BASE_URL = "https://step.focusspoint.com/api/";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseFeedModelList = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String USER = prefs.getString("email","");
        String uid = prefs.getString("uid", "");
        //CheckIfLogin(USER,uid);
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
                        Toast.makeText(getContext(), "Is not Login", Toast.LENGTH_SHORT).show();
                        Intent myintent = new Intent(getContext(),LoginActivity.class);
                        startActivity(myintent);

                    }else  if (status == 200){
                        Toast.makeText(getContext(), "Is Login", Toast.LENGTH_SHORT).show();
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homefragment, container, false);
        initViews();
       courseAdapter = new CourseAdapter(getContext(), this    , courseFeedModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;




    }

    public void initViews(){
        recyclerView = view.findViewById(R.id.rv_course);
        et_search = view.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                serchcall(String.valueOf(editable));
            }
        });
    }


    private void serchcall(String coursename){


        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<CourseFeedModel>> call = ra.getsearchcourse(coursename);
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
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

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
//                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onPositionClicked(int position) {
        String id = courseFeedModelList.get(position).getCId();
        Intent intent = new Intent(getContext(),coursedetail.class);
        intent.putExtra("id",id);
        startActivity(intent);

    }
}
