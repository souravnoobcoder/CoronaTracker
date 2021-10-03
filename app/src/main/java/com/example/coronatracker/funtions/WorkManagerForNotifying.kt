package com.example.coronatracker.funtions


import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.coronatracker.R
import com.example.coronatracker.activities.MainActivity
import com.example.coronatracker.api.Methods
import com.example.coronatracker.api.NewsApi
import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.dataClasses.indiaContactModel.stateContacts
import com.example.coronatracker.dataClasses.indiaModel.indiaStates
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        var loc: String? = null
        var active = 0
        var stateContactNumber: String?
        var confirmedCasesIndian = 0
        var confirmedCasesForeign = 0
        var discharged = 0
        var deaths = 0
        var totalConfirmed = 0
        val country: String = Location.getLocation().country
        val locationState = Location.getLocation().state
        val collapsedNotify =
            RemoteViews(context.packageName, R.layout.collapsed_notifiaction)
        val expandedNotify =
            RemoteViews(context.packageName, R.layout.expanded_notification)
        if (country == "India") {
            print("India yes")
            var state: indiaStates? = null
            var contacts: stateContacts? = null
            val stateCall = NewsApi.indiaState?.create(Methods::class.java)

            stateCall?.getContacts()?.enqueue(object : Callback<stateContacts?> {
                override fun onResponse(
                    call: Call<stateContacts?>,
                    response: Response<stateContacts?>
                ) {
                    contacts = response.body()
                    val stateIndex: Int = ImportantFun.getStateContactIndex(
                        contacts?.data?.contacts?.regional!!,
                        locationState
                    )
                    val stateContacts = contacts?.data?.contacts?.regional!![stateIndex]
                    stateContactNumber = stateContacts?.number
                    expandedNotify.setTextViewText(R.id.locationNameText, loc)
                    expandedNotify.setTextViewText(
                        R.id.recoveredText,
                        discharged.toString()
                    )
                    expandedNotify.setTextViewText(
                        R.id.confirmedCaseText,
                        totalConfirmed.toString()
                    )
                    expandedNotify.setTextViewText(R.id.deathsText, deaths.toString())
                    expandedNotify.setTextViewText(
                        R.id.foreigner_expandedCard,
                        confirmedCasesForeign.toString()
                    )
                    expandedNotify.setTextViewText(
                        R.id.indianCitizen_expandedCard,
                        active.toString()
                    )
                    expandedNotify.setTextViewText(R.id.helpline_expandedCard, stateContactNumber)
                }


                override fun onFailure(call: Call<stateContacts?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            stateCall?.getStates()?.enqueue(object : Callback<indiaStates?> {

                override fun onResponse(
                    call: Call<indiaStates?>,
                    response: Response<indiaStates?>
                ) {
                    state = response.body()
                    val stateIndex: Int =
                        ImportantFun.getStateIndex(state?.data?.regional!!, locationState)
                    val stateValue: com.example.coronatracker.dataClasses.indiaModel.Regional =
                        state?.data?.regional?.get(stateIndex)!!
                    loc = stateValue.loc
                    discharged = stateValue.discharged
                    confirmedCasesIndian = stateValue.confirmedCasesIndian
                    deaths = stateValue.deaths
                    totalConfirmed = stateValue.totalConfirmed
                    confirmedCasesForeign = stateValue.confirmedCasesForeign
                    active = (totalConfirmed - (deaths + discharged))
                    collapsedNotify.setTextViewText(R.id.locationNameText, loc)
                    collapsedNotify.setTextViewText(
                        R.id.recoveredText,
                        discharged.toString()
                    )
                    collapsedNotify.setTextViewText(
                        R.id.confirmedCaseText,
                        totalConfirmed.toString()
                    )
                    collapsedNotify.setTextViewText(R.id.deathsText, deaths.toString())
                    val notificationBuilder =
                        NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                            .setContentTitle(loc)
                            .setContentText("listOfRoot[0].active.toString()" + "\n\nkdjd" + "\n\nkdjd" + "\n\nkdjd")
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

                override fun onFailure(call: Call<indiaStates?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        } else {
            print("India no")
            var listOfRoot: ArrayList<Root>

            print("work manager is called")

            val method = NewsApi.apiInstance?.create(Methods::class.java)

            method?.getData()?.enqueue(object : Callback<List<Root?>> {

                override fun onResponse(call: Call<List<Root?>>, response: Response<List<Root?>>) {
                    print("work manager is called")
                    assert(response.body() != null)
                    listOfRoot = response.body() as ArrayList<Root>
                    val countryIndex = ImportantFun.getCountryIndex(listOfRoot, country)
                    val countryValue: Root = listOfRoot[countryIndex]
                    collapsedNotify.setTextViewText(R.id.locationNameText, countryValue.country)
                    collapsedNotify.setTextViewText(
                        R.id.recoveredText,
                        countryValue.recovered.toString()
                    )
                    collapsedNotify.setTextViewText(
                        R.id.confirmedCaseText,
                        countryValue.cases.toString()
                    )
                    collapsedNotify.setTextViewText(
                        R.id.deathsText,
                        countryValue.deaths.toString()
                    )
                    val notificationBuilder =
                        NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                            .setContentTitle(loc)
                            .setContentText("listOfRoot[0].active.toString()" + "\n\nkdjd" + "\n\nkdjd" + "\n\nkdjd")
                            .setCustomContentView(collapsedNotify)
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
}