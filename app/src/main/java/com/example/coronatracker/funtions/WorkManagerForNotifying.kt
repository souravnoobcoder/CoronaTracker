package com.example.coronatracker.funtions


import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.widget.RemoteViews
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.coronatracker.activities.MainActivity
import com.example.coronatracker.api.Methods
import com.example.coronatracker.api.NewsApi
import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class WorkManagerForNotifying(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

  private var TAG="tag"
  val intent=Intent(context.applicationContext,MainActivity::class.java)
    val pendingIntent:PendingIntent= PendingIntent.getActivity(context,0,intent,0)


    override fun doWork(): Result {
        getData()
        return Result.success()
    }


    private fun getData() {
        var listOfRoot: ArrayList<Root>


        print("work manager is calles")
        val method = NewsApi.apiInstance?.create(Methods::class.java)

        method?.getData()?.enqueue(object : Callback<List<Root?>> {

            override fun onResponse(call: Call<List<Root?>>, response: Response<List<Root?>>) {
                print("work manager is called")
                assert(response.body() != null)
                listOfRoot = response.body() as ArrayList<Root>


                val collapsedNotify = RemoteViews(context.packageName, R.layout.collapsed_notifiaction)
                val expandedNotify = RemoteViews(context.packageName, R.layout.expanded_notification)

                collapsedNotify.setTextViewText(R.id.locationNameText,listOfRoot[0].country)
                collapsedNotify.setTextViewText(R.id.recoveredText,listOfRoot[0].recovered.toString())
                collapsedNotify.setTextViewText(R.id.confirmedCaseText,listOfRoot[0].cases.toString())
                collapsedNotify.setTextViewText(R.id.deathsText,listOfRoot[0].deaths.toString())

                val notificationBuilder =
                    NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                        .setContentTitle(listOfRoot[0].country)
                        .setContentText(listOfRoot[0].active.toString()+"\n\nkdjd"+"\n\nkdjd"+"\n\nkdjd")
                        .setCustomContentView(collapsedNotify)
                        .setCustomBigContentView(expandedNotify)
                        .setSmallIcon(R.drawable.ic_theme)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                with(NotificationManagerCompat.from(context))
                {
                    notify(Random.nextInt(), notificationBuilder.build())
                }
            }
            override fun onFailure(call: Call<List<Root?>>, t: Throwable) {

            }
        })
    }
}