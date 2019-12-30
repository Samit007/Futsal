package com.example.mylocation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class ApiManager {
    private static final ApiManager ourInstance = new ApiManager();

    public static ApiManager getInstance() {
        return ourInstance;
    }

    private ApiManager() {
    }

    public ApiClient getApiClient(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.137.41:4000/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(ApiClient.class);
    }
}