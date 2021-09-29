package com.example.coronatracker.funtions

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.coronatracker.funtions.WorkManagerForNotifying
import java.util.concurrent.TimeUnit

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
        notificationCreator()
        setupWorker()
        //        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//
//                    }
//                })
    }

    private val isNetworkConnected: Boolean
        get() {
            val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting
        }

    private fun notificationCreator() {

        // collapsedNotify.setTextViewText(R.id.deathsTextView,"ngo");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "channel Name", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = "lets see how foreground works"
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(notificationChannel)
        }
    }

    private fun setupWorker() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            WorkManagerForNotifying::class.java, 10, TimeUnit.MINUTES
        )
            .build()
        WorkManager.getInstance(this@MyApplication).enqueue(periodicWorkRequest)
    }

    companion object {
        const val CHANNEL_ID = "myChannel"
        const val KEY = "key_for_theme"

        // private FusedLocationProviderClient fusedLocationClient;
        private var instance: MyApplication? = null
        @JvmStatic
        fun getInstance(): Context? {
            return instance
        }

        @JvmStatic
        fun hasNetwork(): Boolean {
            return instance!!.isNetworkConnected
        }
    }
}