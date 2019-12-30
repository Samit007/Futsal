package com.example.mylocation;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*
This is a Retrofit Interface Class.
*/
public interface ApiClient {

//    @GET("api/get/attendance")
//    Call <Response> getAttendance(
//            @Header("headerauth") String token,
//            @Query("id") Integer id,
//            @Query("employeeID") String empid,
//            @Query("englishDate") String engdate

    @GET("api/tcentre")
    Call<List<TraningCentre>>getTraningCentre();

////////////////////////////////////////////////////////////////////////
//........................POST METHOD ..................................
//    @FormUrlEncoded
//    @POST("api/post/login")
//    @Headers("Content-type:application/x-www-form-urlencoded")
//    Call<List<Employee>> postLogin(
//            @Field("companyID") String companyid,
//            @Field("UserName") String username,
//            @Field("Password") String password
//    );
}