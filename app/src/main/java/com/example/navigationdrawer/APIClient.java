package com.example.navigationdrawer;


import android.content.Context;

import com.example.navigationdrawer.interfaces.Api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final  String BASE_URL = "https://step.focusspoint.com/api/";
    private  static  APIClient mInstance;
    private static Retrofit retrofit;

    private APIClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getRetrofitClient(Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static  synchronized  APIClient getInstance(){
        if (mInstance == null){
            mInstance = new APIClient();
        }
        return  mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }


}

