package com.example.coronatracker.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class newApi {


    private static Retrofit COVID_API_INSTANCE;
    private static Retrofit WORLD;
    private static Retrofit INDIA_STATE;

    public static final String BASE_URL="https://corona.lmao.ninja/v3/";
    public static final String WORLD_URL="https://corona.lmao.ninja/v2/";
    public static final String STATE_URL="https://api.rootnet.in/";

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
    public static Retrofit getIndiaState(){
        if (INDIA_STATE ==null){
            INDIA_STATE =new Retrofit.Builder()
                    .baseUrl(STATE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INDIA_STATE;
    }
}
