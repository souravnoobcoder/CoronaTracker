package com.example.coronatracker.Funtions


import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.coronatracker.Activities.MainActivity
import com.example.coronatracker.Api.methods
import com.example.coronatracker.Api.newApi
import com.example.coronatracker.DataClasses.Root
import com.example.coronatracker.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WorkManagerForNotifying(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    val instance: Context = MyApplication.getInstance()
  val intent=Intent(instance.applicationContext,MainActivity::class.java)
    val pendingIntent:PendingIntent= PendingIntent.getActivity(instance,0,intent,0)
    override fun doWork(): Result {
        getData()
        return Result.success()
    }


    private fun getData() {
        var listOfRoot: java.util.ArrayList<Root>


        print("work manager is calles")
        val method = newApi.getApiInstance().create(methods::class.java)
        method.data.enqueue(object : Callback<List<Root?>> {

            override fun onResponse(call: Call<List<Root?>>, response: Response<List<Root?>>) {
                print("work manager is called")
                assert(response.body() != null)
                listOfRoot = response.body() as ArrayList<Root>


                val collapsedNotify = RemoteViews(instance.packageName, R.layout.collapsed_notifiaction)
                val expandedNotify = RemoteViews(instance.packageName, R.layout.expanded_notification)

                collapsedNotify.setTextViewText(R.id.locationNameText,listOfRoot[0].country)
                collapsedNotify.setTextViewText(R.id.recoveredText,listOfRoot[0].recovered.toString())
                collapsedNotify.setTextViewText(R.id.confirmedCaseText,listOfRoot[0].cases.toString())
                collapsedNotify.setTextViewText(R.id.deathsText,listOfRoot[0].deaths.toString())

                val myNotification =
                    NotificationCompat.Builder(instance, MyApplication.CHANNEL_ID)
                        .setContentTitle(listOfRoot[0].country)
                        .setContentText(listOfRoot[0].active.toString()+"\n\nkdjd"+"\n\nkdjd"+"\n\nkdjd")
                        .setCustomContentView(collapsedNotify)
                        .setCustomBigContentView(expandedNotify)
                        .setSmallIcon(R.drawable.ic_theme)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                with(NotificationManagerCompat.from(instance))
                {
                    notify(1000, myNotification.build())
                }
            }

            override fun onFailure(call: Call<List<Root?>>, t: Throwable) {

            }
        })
    }

}