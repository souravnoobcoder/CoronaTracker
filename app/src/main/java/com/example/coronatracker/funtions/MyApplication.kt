package com.example.coronatracker.funtions

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
        notificationCreator()
        setupWorker()
    }

    /**
     * @return true if connected to network
     * @return false if not connected to any network
     */
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

    /**
     * setting worker for notifying User every 12 hours on behalf of his
     * @location
     */
    private fun setupWorker() {
        val constraint=Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            WorkManagerForNotifying::class.java, 12, TimeUnit.HOURS
        ).setConstraints(constraint)
            .setInitialDelay(6,TimeUnit.HOURS)
            .addTag(WORKER_TAG)
            .setBackoffCriteria(BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            SYNC_DATA_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    companion object {
        const val WORKER_TAG = ""
        const val CHANNEL_ID = "myChannel"
        const val SYNC_DATA_WORK_NAME = "this is unique"

        // private FusedLocationProviderClient fusedLocationClient;
        private var instance: MyApplication? = null

        /**
         * @return instance of application
         */
        @JvmStatic
        fun getInstance(): Context? {
            return instance
        }

        /**
         * @return true if connected to network
         * @return false if not connected to any network
         */
        @JvmStatic
        fun hasNetwork(): Boolean {
            return instance!!.isNetworkConnected
        }
    }
}