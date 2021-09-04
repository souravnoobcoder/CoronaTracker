package com.example.coronatracker.Api;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.net.ConnectivityManager;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class newApi {


    private static final long cacheSize = (1024 * 1024);
    private static final Cache cache=new Cache(MyApplication.getInstance().getCacheDir(),cacheSize);
    private static final OkHttpClient client=new OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (MyApplication.hasNetwork())
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                    else
                        request.newBuilder().header("Cache-Control", "public, " +
                                "only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    chain.proceed(request);
                    return null;
                }
            }).build();

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
                    .client(client)
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
