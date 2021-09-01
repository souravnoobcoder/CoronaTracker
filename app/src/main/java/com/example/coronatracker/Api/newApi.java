package com.example.coronatracker.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class newApi {
    private static Retrofit COVID_API_INSTANCE;
    public static final String BASE_URL="https://corona.lmao.ninja/v3/";
    public static Retrofit getApiInstance(){
        if (COVID_API_INSTANCE==null){
            COVID_API_INSTANCE=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return COVID_API_INSTANCE;
    }
}
