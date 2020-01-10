package com.example.mylocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

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

    @GET("api/hospital")
    Call<List<Hospital>> getHospital();

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