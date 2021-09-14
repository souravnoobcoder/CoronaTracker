package com.example.coronatracker.Funtions;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.coronatracker.Activities.MainActivity;

import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "myChannel";
    public static final String KEY = "key_for_theme";
    // private FusedLocationProviderClient fusedLocationClient;
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
        notificationCreator();
        setupWorker();
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//
//                    }
//                })
    }

    public static Context getInstance(){
        return instance;
    }

    public static boolean hasNetwork(){
        return instance.isNetworkConnected();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void notificationCreator() {

        // collapsedNotify.setTextViewText(R.id.deathsTextView,"ngo");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    "channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("lets see how foreground works");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    void setupWorker() {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
                .Builder(WorkManagerForNotifying.class, 10, TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }
}
