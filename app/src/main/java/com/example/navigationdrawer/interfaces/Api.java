package com.example.navigationdrawer.interfaces;


import com.example.navigationdrawer.Model.AttendeceModel;
import com.example.navigationdrawer.Model.GetDropdownCoursesModel;
import com.example.navigationdrawer.Model.MeetingModal;
import com.example.navigationdrawer.Model.OnlineSessionModal;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> createUser(
            @Field("username") String Username,
            @Field("password") String Password,
            @Field("device_id") String device_id,
            @Field("device_type") String device_type,
            @Field("device_token") String device_token,
            @Field("login") String Login,
            @Field("role") Integer Role
    );

    @Multipart
    @POST("create-task")
    Call<ResponseBody> createTask(
            @Part("save_task") RequestBody save_task,
            @Part("task") RequestBody task,
            @Part("taskdate") RequestBody taskdate,
            @Part("user") RequestBody user,
            @Part("from") RequestBody from,
            @Part("to") RequestBody to,
            @Part MultipartBody.Part record
    );

    @Multipart
    @POST("create-task")
    Call<ResponseBody> createTaskWithout(
            @Part("save_task") RequestBody save_task,
            @Part("task") RequestBody task,
            @Part("taskdate") RequestBody taskdate,
            @Part("user") RequestBody user,
            @Part("from") RequestBody from,
            @Part("to") RequestBody to
    );



    @FormUrlEncoded
    @POST("logout")
    Call<ResponseBody> logout(
            @Field("username") String Username,
            @Field("logout") String logout,
            @Field("device_id") String device_id
    );


    @FormUrlEncoded
    @POST("check-login")
    Call<ResponseBody> checkLogin(
            @Field("username") String username,
            @Field("device_id") String device_id
    );


    @FormUrlEncoded
    @POST("profile")
    Call<ResponseBody> profile(
            @Field("login") String login

    );

    @GET("get_all_meetings/")
    Call<List<MeetingModal>> getallmeeting();

    @GET("get_all_meetings/")
    Call<List<OnlineSessionModal>> getallonlinesession();



    @GET("get_dropdown_course/")
    Call<List<GetDropdownCoursesModel>> get_dropdown_course();

    @FormUrlEncoded
    @POST("queryform/")
    Call<ResponseBody> queryform(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("email") String email,
            @Field("mobile") String number,
            @Field("lq") String lq,
            @Field("course") String course,
            @Field("cins") String cins
    );
}

