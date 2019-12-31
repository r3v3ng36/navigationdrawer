package com.example.navigationdrawer;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.navigationdrawer.Model.InvoiceModel;
import com.example.navigationdrawer.interfaces.redditapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterInvoice extends AppCompatActivity {


    String BASE_URL = "https://step.focusspoint.com/api/";
    String USER;
    TextView email,name,useremail,number,discription,refernceno,
            rate,paid,balance,amount3,amount1,amount2,datetop,date,invoiceno,reciept_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_register_invoice);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        useremail = findViewById(R.id.useremail);
        number = findViewById(R.id.number);
        discription = findViewById(R.id.discription);
        refernceno = findViewById(R.id.refernceno);
        rate = findViewById(R.id.rate);
        paid = findViewById(R.id.paid);
        balance = findViewById(R.id.balance);
        amount1 = findViewById(R.id.amount1);
        amount2 = findViewById(R.id.amount2);
        amount3 = findViewById(R.id.amount3);
        datetop = findViewById(R.id.datetop);
        date = findViewById(R.id.date);
        invoiceno = findViewById(R.id.invoiceno);
        reciept_amount = findViewById(R.id.reciept_amount);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        USER = prefs.getString("email","");
        ServiceCall();

    }

    public  void ServiceCall(){

        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditapi ra = rf.create(redditapi.class);
        Call<List<InvoiceModel>> call = ra.getinvoicefeed(USER);
        call.enqueue(new Callback<List<InvoiceModel>>() {
            @Override
            public void onResponse(Call<List<InvoiceModel>> call, Response<List<InvoiceModel>> response) {
                if(!response.isSuccessful()){

                    return;
                }

                List<InvoiceModel> feeds = response.body();
                refernceno.setText(feeds.get(0).getSIdno());
                email.setText(feeds.get(0).getSEmail());
                name.setText(feeds.get(0).getSFname() + "  " + feeds.get(0).getSLname());
                useremail.setText(feeds.get(0).getSEmail());
                number.setText(feeds.get(0).getSMobileNo());
                discription.setText(feeds.get(0).getCTitle());
                rate.setText(feeds.get(0).getSFeesamount());
                paid.setText("PKR "+feeds.get(0).getSFeespaid());
                int amountfee = Integer.parseInt(feeds.get(0).getSFeesamount());
                int paidfee = Integer.parseInt(feeds.get(0).getSFeespaid());
                int balanceremaing = amountfee - paidfee;
                balance.setText("PKR "+balanceremaing);
                amount1.setText(feeds.get(0).getSFeesamount());
                amount3.setText("PKR "+feeds.get(0).getSFeesamount());
                amount2.setText(feeds.get(0).getSFeesamount());
                date.setText(feeds.get(0).getDate());
                datetop.setText(feeds.get(0).getDate());
                invoiceno.setText(feeds.get(0).getSIdno());
                reciept_amount.setText(feeds.get(0).getSFeespaid());



            }




            @Override
            public void onFailure(Call<List<InvoiceModel>> call, Throwable t) {
                Log.d("Failure response : ",t.toString());
                // tv.setText(t.getMessage());
            }
        });

    }
    }

