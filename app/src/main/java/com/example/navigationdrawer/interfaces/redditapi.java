package com.example.navigationdrawer.interfaces;

import android.widget.Filter;
import android.widget.ScrollView;

import com.example.navigationdrawer.Model.AllNotiModel;
import com.example.navigationdrawer.Model.AttendeceModel;
import com.example.navigationdrawer.Model.CompletedTaskModel;
import com.example.navigationdrawer.Model.CountModel;
import com.example.navigationdrawer.Model.CourseFeedModel;
import com.example.navigationdrawer.Model.DaysModel;
import com.example.navigationdrawer.Model.EmployeeListModel;
import com.example.navigationdrawer.Model.FeedModel;
import com.example.navigationdrawer.Model.FilterModel;
import com.example.navigationdrawer.Model.InvoiceModel;
import com.example.navigationdrawer.Model.MagzineModel;
import com.example.navigationdrawer.Model.PendingTaskModel;
import com.example.navigationdrawer.Model.ProcessTaskModel;
import com.example.navigationdrawer.Model.RulesModel;
import com.example.navigationdrawer.Model.coursedetailfeedModel;
import com.example.navigationdrawer.Model.noticefeedModel;
import com.example.navigationdrawer.Model.notificationfeedModel;

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

public interface redditapi {

    @GET("profile/")
    Call<List<FeedModel>> getfeed(
            @Query("username") String username);

    @GET("course-detail/")
    Call<List<DaysModel>> course(
            @Query("course") String course);

    @GET("notices/")
    Call<List<noticefeedModel>> getnoticefeed(
            @Query("username") String username);

    @GET("registration-invoice/")
    Call<List<InvoiceModel>> getinvoicefeed(@Query("username") String username);
//
    @GET("notifications/")
   Call<List<notificationfeedModel>> getnotifeed(
           @Query("username") String username);



    @GET("editorial-magazine/")
    Call<List<MagzineModel>> getmagazinefeed(@Query("username") String username);

    @GET("preview-attendance/")
    Call<List<AttendeceModel>> getattendancefeed(@Query("username") String username);
//
    @GET("upcoming-course-detail/")
    Call<List<coursedetailfeedModel>> getcoursedetail(
            @Query("course") String course
    );

    @GET("upcoming")
    Call<List<CourseFeedModel>> getcoursefeed();

    @GET("global-notices/")
    Call<List<AllNotiModel>> getallnoticefeed();







    @GET("completed-task/")
    Call<List<CompletedTaskModel>> getcompletedtask();

    @GET("pending-task/")
    Call<List<PendingTaskModel>> getpendingtask();

    @GET("inprogress-task/")
    Call<List<ProcessTaskModel>> getprocesstask();

    @GET("employees/")
    Call<List<EmployeeListModel>> getemployeelist();

    @GET("rules-regulations")
    Call<List<RulesModel>> rules();

    @GET("search-course/")
    Call<List<CourseFeedModel>> getsearchcourse(
            @Query("course") String course
    );

    @GET("upcoming-count/")
    Call<List<CountModel>> getUpcomingCount(

    );

    @GET("attendance-count/")
    Call<List<CountModel>> getAttendencecount(
            @Query("username") String username
    );

    @GET("editorial-magazine-count/")
    Call<List<CountModel>> getMagzineCount(
            @Query("username") String username
    );

    @GET("notice-count/")
    Call<List<CountModel>> getNoticesCount(
            @Query("username") String username
    );

    @GET("filter-task/")
    Call<List<FilterModel>> getsearch(
            @Query("emp_id") String emp_id,
            @Query("from") String from,
            @Query("to") String to,
            @Query("status") String status

    );



}
