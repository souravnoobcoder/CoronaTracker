package com.example.coronatracker.funtions


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.coronatracker.activities.MainActivity
import com.example.coronatracker.Api.methods
import com.example.coronatracker.Api.newApi
import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class WorkManagerForNotifying(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

  private var TAG="tag"
    private lateinit var fusedLocationProvider:FusedLocationProviderClient
  val intent=Intent(context.applicationContext,MainActivity::class.java)
    val pendingIntent:PendingIntent= PendingIntent.getActivity(context,0,intent,0)


    override fun doWork(): Result {
        getData()
        return Result.success()
    }


    private fun getData() {
        fusedLocationProvider=LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(context)
                .setTitle("Location Permission Needed")
                .setMessage("This app needs the Location permission, please accept to use location functionality")
                .setPositiveButton(
                    "OK"
                ) { _, _ ->
                    //Prompt the user once explanation has been shown
                    requestLocationPermission()
                }
                .create()
                .show()
            return
        }
        fusedLocationProvider.lastLocation
            .addOnSuccessListener {
                location : Location->
                val geocoder=Geocoder(context, Locale.getDefault())
                val listOfAddress:List<Address> =
                    geocoder.getFromLocation(location.latitude, location.longitude,1)
                val address=listOfAddress[0]
                val country=address.countryName
                val state= address.adminArea
                val district=address.extras
            }

        var listOfRoot: java.util.ArrayList<Root>


        print("work manager is calles")
        val method = newApi.getApiInstance().create(methods::class.java)
        method.data.enqueue(object : Callback<List<Root?>> {

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

                val myNotification =
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
                    notify(1000, myNotification.build())
                }
            }

            override fun onFailure(call: Call<List<Root?>>, t: Throwable) {

            }
        })
    }

    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )

        }
    }
    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }
}