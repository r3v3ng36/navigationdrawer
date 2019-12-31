package com.example.navigationdrawer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Adapter.CompletedtaskAdapter;
import com.example.navigationdrawer.Adapter.EmployeeListAdapter;
import com.example.navigationdrawer.Adapter.FilterAdapter;
import com.example.navigationdrawer.Model.CompletedTaskModel;
import com.example.navigationdrawer.Model.EmployeeListModel;
import com.example.navigationdrawer.Model.FilterModel;
import com.example.navigationdrawer.interfaces.redditapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalendarFragment extends Fragment {
    View view;
//Service Call
    String a,b;
    private List<EmployeeListModel> employeeListModels;
    private RecyclerView recyclerView;
    EmployeeListAdapter eta;
    List<String> categories = new ArrayList<String>();
    List<String> username = new ArrayList<String>();
    String BASE_URL = "https://step.focusspoint.com/api/employees/";
    String USER = "";
    Spinner spinner;
    Spinner spinnerone;
    ArrayAdapter<String> dataAdapter;
    Button selectDate;
    Button select_to;
    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialogs;
    int year;
    int month;
    Button submitbtn;
    int dayOfMonth;
    Calendar calendar;
    Button submit;

//Search Call
    private List<FilterModel> filterModels;
    FilterAdapter fta;
    String BASE_URLS = "https://step.focusspoint.com/api/filter-task/";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendarfragment, container, false);
        filterModels = new ArrayList<>();
        selectDate = view.findViewById(R.id.fromdate);
        submit = view.findViewById(R.id.et_search);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterModels.clear();
                searchCall();

            }
        });
        select_to = view.findViewById(R.id.todate);
        eta = new EmployeeListAdapter(getContext(), employeeListModels);
        spinner = view.findViewById(R.id.spinner);
        spinnerone = view.findViewById(R.id.spinner1);
        List<String> selection = new ArrayList<String>();
        selection.add("All");
        selection.add("Complete");
        selection.add("Pending");
        selection.add("Inprogress");
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                selectDate.setText(year + "/" + (month + 1) + "/" + day);
                            }
                        }, year, month, dayOfMonth);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        select_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialogs = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                select_to.setText(year + "/" + (month + 1) + "/" + day);
                            }
                        }, year, month, dayOfMonth);
//                datePickerDialogs.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialogs.show();
            }
        });



        ArrayAdapter<String> sdataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, selection);
        sdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerone.setAdapter(sdataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(),"Clicked = " + position,Toast.LENGTH_LONG).show();
                spinner.setSelection(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//SearchCall
        recyclerView = view.findViewById(R.id.rv_completed_task);
        fta = new FilterAdapter(getContext(), filterModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(fta);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        employeeListModels = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        USER = preferences.getString("email", "");
        String uid = preferences.getString("uid", "");
        CheckIfLogin(USER,uid);
        serviceCall();
        return view;
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

    @Override
    public void onResume() {
        super.onResume();

    }


    private void searchCall() {
        String[] mystring = spinner.getSelectedItem().toString().split("-");

        a=mystring[0];
        b=mystring[1];
        Toast.makeText(getContext(),a + b,Toast.LENGTH_LONG).show();
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URLS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);

        if(select_to.getText().equals("To") && selectDate.getText().equals("From")){


            Call<List<FilterModel>> call = ra.getsearch(b,"","",spinnerone.getSelectedItem().toString());
            call.enqueue(new Callback<List<FilterModel>>() {
                @Override
                public void onResponse(Call<List<FilterModel>> call, Response<List<FilterModel>> response) {
                    if (!response.isSuccessful()) {
                        return;
                    } else if (response.isSuccessful()) {
                        List<FilterModel> feeds = response.body();
                        if (!feeds.get(0).getT_status().isEmpty()) {
                            for (int i = 0; i < feeds.size(); i++) {
                                filterModels.add(new FilterModel(
                                        feeds.get(i).getEmployee(),
                                        feeds.get(i).getTask_detail(),
                                        feeds.get(i).getRecording(),
                                        feeds.get(i).getAssign_date(),
                                        feeds.get(i).getAction_date(),
                                        feeds.get(i).getT_status()));
                                fta.notifyDataSetChanged();
                            }
                        }
                    }else{
                        Toast.makeText(getContext(),"Search not Found.",Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<List<FilterModel>> call, Throwable t) {
                    Log.d("Failure response : ", t.toString());
                    Toast.makeText(getContext(),"Search not Found.",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Call<List<FilterModel>> call = ra.getsearch(b,selectDate.getText().toString(),select_to.getText().toString(),spinnerone.getSelectedItem().toString());
            call.enqueue(new Callback<List<FilterModel>>() {
                @Override
                public void onResponse(Call<List<FilterModel>> call, Response<List<FilterModel>> response) {
                    if (!response.isSuccessful()) {
                        return;
                    } else if (response.isSuccessful()) {
                        List<FilterModel> feeds = response.body();
                        if (!feeds.get(0).getT_status().isEmpty()) {
                            for (int i = 0; i < feeds.size(); i++) {
                                filterModels.add(new FilterModel(
                                        feeds.get(i).getEmployee(),
                                        feeds.get(i).getTask_detail(),
                                        feeds.get(i).getRecording(),
                                        feeds.get(i).getAssign_date(),
                                        feeds.get(i).getAction_date(),
                                        feeds.get(i).getT_status()));
                                fta.notifyDataSetChanged();
                            }
                        }
                    }else{
                        Toast.makeText(getContext(),"Search not Found.",Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<List<FilterModel>> call, Throwable t) {
                    Log.d("Failure response : ", t.toString());
                    Toast.makeText(getContext(),"Search not Found.",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }



    private void serviceCall() {

        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<EmployeeListModel>> call = ra.getemployeelist();
        call.enqueue(new Callback<List<EmployeeListModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeListModel>> call, Response<List<EmployeeListModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                } else if (response.isSuccessful()) {
                    List<EmployeeListModel> feeds = response.body();
                    if (!feeds.get(0).getU_fname().isEmpty()) {
                        for (int i = 0; i < feeds.size(); i++) {
                            employeeListModels.add(new EmployeeListModel(
                                    feeds.get(i).getU_id(),feeds.get(i).getU_fname(),feeds.get(i).getU_lname(),feeds.get(i).getU_username()));
                            categories.add(feeds.get(i).getU_username() + "-" + feeds.get(i).getU_id().toString());
//                            username.add();
//                            HashMap map=new HashMap();
//                            map.put(categories,username);
                            eta.notifyDataSetChanged();
                        }

                    }
                    spinner.setAdapter(dataAdapter);
                }else{
                    Toast.makeText(getContext(),"There is no notices",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<List<EmployeeListModel>> call, Throwable t) {
                Log.d("Failure response : ", t.toString());
                Toast.makeText(getContext(),"There is no notices",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
