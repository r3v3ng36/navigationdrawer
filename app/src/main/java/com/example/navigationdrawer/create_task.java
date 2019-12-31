package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.navigationdrawer.Adapter.EmployeeListAdapter;
import com.example.navigationdrawer.Model.EmployeeListModel;
import com.example.navigationdrawer.interfaces.Api;
import com.example.navigationdrawer.interfaces.redditapi;
import com.haozhang.lib.AnimatedRecordingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class create_task extends AppCompatActivity {

    String a,ab;
    private List<EmployeeListModel> employeeListModels;
    private RecyclerView recyclerView;
    EmployeeListAdapter eta;
    List<String> categories = new ArrayList<String>();
    List<String> username = new ArrayList<String>();
    String BASE_URL = "https://step.focusspoint.com/api/employees/";
    String USER = "";
    Spinner spinner;
    ArrayAdapter<String> dataAdapter;
    Button selectDate;
    Button select_to,et_search;
    DatePickerDialog datePickerDialogss;
    DatePickerDialog datePickerDialogsss;
    EditText mytext;
    int year;
    int month;
    Chronometer chronometer;
    ImageButton buttonStart, buttonStop, buttonPlayLastRecordAudio,
            buttonStopPlayingRecording ,btn_pause;
    Button uploadbtn;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    File files;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    ImageView back_btn;
    private static final int READ_REQUEST_CODE = 42;

    int dayOfMonth;
    Calendar calendar;
    AnimatedRecordingView mRecordingView;


    //Search Call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_create_task);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent myintent = new Intent(create_task.this,task_management.class);
                startActivity(myintent);
            }
        });
        mRecordingView = findViewById(R.id.recording);
        et_search = findViewById(R.id.et_search);
        mytext = findViewById(R.id.enter_task);
        chronometer=findViewById(R.id.chronometer);

//        et_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginCall();
//            }
//        });

        selectDate = findViewById(R.id.fromdate);
        select_to = findViewById(R.id.todate);
        eta = new EmployeeListAdapter(getApplicationContext(), employeeListModels);
        spinner = findViewById(R.id.spinner);
        buttonStart = findViewById(R.id.button);
        buttonStop = findViewById(R.id.button2);
        buttonPlayLastRecordAudio = findViewById(R.id.button3);
        buttonStopPlayingRecording = findViewById(R.id.btn_pause);

        buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);

        random = new Random();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkPermission()) {

                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                    CreateRandomAudioFileName(5) + "AudioRecording.mp3";
                    files = new File(AudioSavePathInDevice);

//                    Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////                    buttonStart.setSoundEffectsEnabled(alarmsound);
//                    final MediaPlayer mp = MediaPlayer.create(this,R.raw.twinkle);
                    MediaRecorderReady();

                    try {
                        mRecordingView.start();
                        mRecordingView.loading();
                        float vol;
                        mRecordingView.setVolume(5);
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();

                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

//                    Toast.makeText(create_task.this, "Recording started",
//                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }

            }
        });
        uploadbtn = findViewById(R.id.upload);
        uploadbtn.setEnabled(false);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AudioSavePathInDevice == null){
                    uploadToServerWithout();
                }
                else{
                    try{
                        uploadToServer(AudioSavePathInDevice);
                    }
                    catch (Exception ex){
                        Toast.makeText(getApplicationContext(),"Select All Fields First.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRecordingView.stop();
                mediaRecorder.stop();
                chronometer.stop();
                buttonStop.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),notification);
                r.play();

//                Toast.makeText(create_task.this, "Recording Completed",
//                        Toast.LENGTH_LONG).show();
            }
        });

        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                buttonStop.setEnabled(false);
                buttonStart.setEnabled(false);
                buttonStopPlayingRecording.setEnabled(true);

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(create_task.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();
            }
        });

        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);

                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialogss = new DatePickerDialog(create_task.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                selectDate.setText(year + "/" + (month + 1) + "/" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialogss.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialogss.show();
            }
        });
        select_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialogsss = new DatePickerDialog(create_task.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                select_to.setText(year + "/" + (month + 1) + "/" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialogsss.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialogsss.show();
                uploadbtn.setEnabled(true);
            }
        });
        ArrayAdapter<String> sdataAdapter = new ArrayAdapter<String>(create_task.this, R.layout.spinner_item, categories);
        sdataAdapter.setDropDownViewResource(R.layout.spinner_item);

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
        dataAdapter = new ArrayAdapter<String>(create_task.this, R.layout.spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        employeeListModels = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        USER = preferences.getString("email", "");
        String uid = preferences.getString("uid", "");
        CheckIfLogin(USER,uid);
        serviceCall();

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
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("email");
                        editor.remove("password");
                        editor.putString("attendeceCount","0");
                        editor.putString("magzineCount","0");
                        editor.putString("upcomingCount","0");
                        editor.putString("noticesCount","0");

                        editor.clear();
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Sorry, you have been logged out..", Toast.LENGTH_SHORT).show();
                        Intent myintent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(myintent);

                    }else  if (status == 200){
//                        Toast.makeText(getContext(), "Is Login", Toast.LENGTH_SHORT).show();
                        return;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

    public  void uploadToServerWithout(){



        try{
            String[] mystring = spinner.getSelectedItem().toString().split("-");
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            a=mystring[0];
            ab=mystring[1];
            Retrofit retrofit = APIClient.getRetrofitClient(this);
            Api api = retrofit.create(Api.class);
            //Create a file object using file path
            //Create request body with text description and text media type
            RequestBody save_task = RequestBody.create(MediaType.parse("text/plain"), "ok");
            RequestBody task = RequestBody.create(MediaType.parse("text/plain"), mytext.getText().toString());
            RequestBody taskdate = RequestBody.create(MediaType.parse("text/plain"), date);
            RequestBody b = RequestBody.create(MediaType.parse("text/plain"), ab);
            RequestBody select_date = RequestBody.create(MediaType.parse("text/plain"), selectDate.getText().toString());
            RequestBody select_to_date = RequestBody.create(MediaType.parse("text/plain"),  select_to.getText().toString());
            //
            Call call = api.createTaskWithout(save_task,task,taskdate,b,select_date,select_to_date);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    Toast.makeText(getApplicationContext(),"Task Has Been Created Successfully.",Toast.LENGTH_LONG).show();
                    mytext.setText("");
                    ab = null;
                    selectDate.setText("From Date");
                    select_to.setText("To Date");
                }
                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error While Creating Task.",Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
        }



    }
    public  void uploadToServer(String filepath){



        try{
            String[] mystring = spinner.getSelectedItem().toString().split("-");
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            a=mystring[0];
            ab=mystring[1];
            Retrofit retrofit = APIClient.getRetrofitClient(this);
            Api api = retrofit.create(Api.class);
            //Create a file object using file path
            File file = new File(filepath);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("mp3/*"), file);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part = MultipartBody.Part.createFormData("record", file.getName(), fileReqBody);
            //Create request body with text description and text media type
            RequestBody save_task = RequestBody.create(MediaType.parse("text/plain"), "ok");
            RequestBody task = RequestBody.create(MediaType.parse("text/plain"), mytext.getText().toString());
            RequestBody taskdate = RequestBody.create(MediaType.parse("text/plain"), date);
            RequestBody b = RequestBody.create(MediaType.parse("text/plain"), ab);
            RequestBody select_date = RequestBody.create(MediaType.parse("text/plain"), selectDate.getText().toString());
            RequestBody select_to_date = RequestBody.create(MediaType.parse("text/plain"),  select_to.getText().toString());
            //
            Call call = api.createTask(save_task,task,taskdate,b,select_date,select_to_date,part);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    Toast.makeText(getApplicationContext(),"Task Has Been Created Successfully.",Toast.LENGTH_LONG).show();
                    mytext.setText("");
                    ab = null;
                    selectDate.setText("From Date");
                    select_to.setText("To Date");
                }
                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error While Creating Task.",Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
        }



    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(create_task.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(create_task.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(create_task.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
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
                    Toast.makeText(getApplicationContext(),"There is no notices",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<List<EmployeeListModel>> call, Throwable t) {
                Log.d("Failure response : ", t.toString());
                Toast.makeText(getApplicationContext(),"There is no notices",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
