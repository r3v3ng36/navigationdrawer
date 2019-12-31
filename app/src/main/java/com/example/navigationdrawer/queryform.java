package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.navigationdrawer.Model.GetDropdownCoursesModel;
import com.example.navigationdrawer.Model.GetDropdownCoursesModel;
import com.example.navigationdrawer.Model.QueryFormModal;
import com.example.navigationdrawer.interfaces.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class queryform extends AppCompatActivity {

    String BASE_URL = "https://step.focusspoint.com/api/";
    ArrayList<GetDropdownCoursesModel> GetDropdownCoursesModelList;
    List<String> itemsList;
    Spinner spinner;
    Button submit;
    EditText fname,lname,lq,cins,email,number;
    QueryFormModal queryFormModal;
    String selectedItemText;
    ArrayAdapter<String> spinnerArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_queryform);

        GetDropdownCoursesModelList = new ArrayList<>();
        ServiceCall();


    }

    public void init(){

        spinner = findViewById(R.id.spinner1);
        submit = findViewById(R.id.btnsubmit);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        number = findViewById(R.id.number);
        lq = findViewById(R.id.lq);
        cins = findViewById(R.id.cins);
        email = findViewById(R.id.email);
        itemsList = new ArrayList<>();
        itemsList.add("Select Course");
        for (GetDropdownCoursesModel getDropdownCoursesModel:GetDropdownCoursesModelList) {
            itemsList.add(getDropdownCoursesModel.getTitle());
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFormData();

            }
        });
        // Initializing an ArrayAdapter
         spinnerArrayAdapter = new ArrayAdapter<>(
                queryform.this, R.layout.spinner_item, itemsList);
        spinner.setSelection(0);
        spinner.setAdapter(spinnerArrayAdapter);


    }

    public void ServiceCall(){

        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = rf.create(Api.class);
        Call<List<GetDropdownCoursesModel>> call = api.get_dropdown_course();
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<List<GetDropdownCoursesModel>>() {
            @Override
            public void onResponse(Call<List<GetDropdownCoursesModel>> call, Response<List<GetDropdownCoursesModel>> response) {
                if(!response.isSuccessful()){

                    return;
                }
                else if (response.isSuccessful()){
                    List<GetDropdownCoursesModel> meetingList = response.body();

                    for (GetDropdownCoursesModel meeting: meetingList) {
                        int i = 0;
                        GetDropdownCoursesModelList.add( new GetDropdownCoursesModel(
                                meeting.getId(),
                                meeting.getTitle()
                        ));
                        i = i++;
                    }

                    init();
                }
            }

            @Override
            public void onFailure(Call<List<GetDropdownCoursesModel>> call, Throwable t) {
                Log.d("Failure response : ",t.toString());

            }
        });




    }

    private void getFormData(){

        if (formValidation()){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(queryform.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("ParticipantName",fname.getText().toString().trim()+" "+lname.getText().toString().trim());
            editor.apply();
            queryFormModal = new QueryFormModal(
                    fname.getText().toString().trim(),
                    lname.getText().toString().trim(),
                    email.getText().toString().trim(),
                    number.getText().toString().trim(),
                    cins.getText().toString().trim(),
                    lq.getText().toString().trim(),
                    spinner.getSelectedItem().toString());
            postQueryForm();
        }else {
        }


    }

    public void postQueryForm(){

        Call<ResponseBody> call = APIClient
                .getInstance()
                .getApi()
                .queryform(
                        queryFormModal.getFname(),
                        queryFormModal.getLname(),
                        queryFormModal.getEmail(),
                        queryFormModal.getMobile(),
                        queryFormModal.getLq(),
                        getSelectedItemId(),
                        queryFormModal.getCins());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent intent= new Intent(queryform.this,ZoomMainActivity.class);
                startActivity(intent);
                queryform.this.finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.wtf("URL Called", call.request().url() + "");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public boolean formValidation(){
        String msg ;

        if ( fname.getText().toString().trim().equals("")){
            msg = "Please enter first name.";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            return false;
        }
        if (lname.getText().toString().trim().equals("")){
            msg = "Please enter last name.";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isValidMobile(number.getText().toString().trim())){
            msg = "Please enter the correct number format";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            return false;
        }
        if (!isValidMail(email.getText().toString().trim())){
            msg = "Please enter the email correct format";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            return false;
        }
        if (spinner.getSelectedItem().toString().equals("Select Course")){
            msg = "Please select the course.";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            return false;
        }

        if (cins.getText().toString().trim().equals("")){
            msg = "Please enter current institute.";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            return false;
        }
        if ( lq.getText().toString().trim().equals("")){
            msg = "Please enter last qualification.";
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public String getSelectedItemId(){
        int id=0;
        for (GetDropdownCoursesModel getDropdownCoursesModel:GetDropdownCoursesModelList) {

            if (getDropdownCoursesModel.getTitle().equals(spinner.getSelectedItem().toString())){
                id = getDropdownCoursesModel.getId();
            }
        }

        return String.valueOf(id);
    }

    private boolean isValidMail(String email) {

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (Pattern.compile(EMAIL_STRING).matcher(email).matches()){
            return true;

        }

        return false;

    }

    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone) && !phone.trim().equals("")) {
            return phone.length() > 6 && phone.length() <= 13;
        }
        return false;
    }
}
