package com.example.coronatracker.Api;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.net.ConnectivityManager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class newApi {


    private static final long cacheSize = (1024 * 1024);        // 1 MB
    private static newApi instance;
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";
    public static newApi getInstance(){
        if(instance == null){
            instance = new newApi();
        }
        return instance;
    }
    private static OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();
    }
    private static OkHttpClient okHttpClientCountries(){
        return new OkHttpClient.Builder()
                .cache(cacheCountry())
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();
    }
    private static OkHttpClient okHttpClientStates(){
        return new OkHttpClient.Builder()
                .cache(cacheState())
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();
    }
    private static Cache cache(){
        return new Cache(new File(MyApplication.getInstance().getCacheDir(),"someIdentifier"), cacheSize);
    }
    private static Cache cacheCountry(){
        return new Cache(new File(MyApplication.getInstance().getCacheDir(),"country"), cacheSize);
    }
    private static Cache cacheState(){
        return new Cache(new File(MyApplication.getInstance().getCacheDir(),"state"), cacheSize);
    }
    /**
     * This interceptor will be called both if the network is available and if the network is not available
     * @return
     */
    private static Interceptor offlineInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                System.out.println("offline interceptor: called.");
                Request request = chain.request();

                // prevent caching when network is on. For that we use the "networkInterceptor"
                if (!MyApplication.hasNetwork()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }
    /**
     * This interceptor will be called ONLY if the network is available
     * @return
     */
    private static Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                System.out.println("network interceptor: called.");

                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(5, TimeUnit.SECONDS)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }
    private static HttpLoggingInterceptor httpLoggingInterceptor ()
    {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
                {
                    @Override
                    public void log (String message)
                    {
                        System.out.println("log: http log: " + message);
                    }
                } );
        httpLoggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
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
                    .client(okHttpClientCountries())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return COVID_API_INSTANCE;
    }
    public static Retrofit getWorld(){
        if (WORLD==null){
            WORLD=new Retrofit.Builder()
                    .baseUrl(WORLD_URL)
                    .client(okHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return WORLD;
    }
    public static Retrofit getIndiaState(){
        if (INDIA_STATE ==null){
            INDIA_STATE =new Retrofit.Builder()
                    .baseUrl(STATE_URL)
                    .client(okHttpClientStates())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INDIA_STATE;
    }
}
