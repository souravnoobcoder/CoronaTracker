package com.example.coronatracker.Funtions;

import static com.example.coronatracker.Activities.MainActivity.TAGO;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyApplication extends Application {
    private static MyApplication instance;
    public static final String KEY="key_for_theme";
    @Override
    public void onCreate() {
        super.onCreate();
    if (instance == null) {
        instance = this;
    }
    }

    public static Context getInstance(){
        return instance;
    }

    public static boolean hasNetwork(){
        return instance.isNetworkConnected();
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
