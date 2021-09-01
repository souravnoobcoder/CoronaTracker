package com.example.coronatracker.Api;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class newApi {


    private static Retrofit COVID_API_INSTANCE;
    private static Retrofit WORLD;
    public static final String BASE_URL="https://corona.lmao.ninja/v3/";
    public static final String WORLD_URL="https://corona.lmao.ninja/v2/";
    public static Retrofit getApiInstance(){
        if (COVID_API_INSTANCE==null){
            COVID_API_INSTANCE=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return COVID_API_INSTANCE;
    }
    public static Retrofit getWorld(){
        if (WORLD==null){
            WORLD=new Retrofit.Builder()
                    .baseUrl(WORLD_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return WORLD;
    }
}
