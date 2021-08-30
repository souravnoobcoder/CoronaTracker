package com.example.coronatracker.Api;

import retrofit2.Retrofit;

public class CovidApi {
    private static Retrofit COVID_API_INSTANCE;
    public static final String BASE_URL="https://api.covid19api.com/";
    public static Retrofit getApiInstance(){
        if (COVID_API_INSTANCE==null){
            COVID_API_INSTANCE=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }
        return COVID_API_INSTANCE;
    }
}
