package com.example.coronatracker.api

import com.example.coronatracker.dataClasses.Root
import retrofit2.http.GET
import com.example.coronatracker.dataClasses.world
import com.example.coronatracker.dataClasses.indiaContactModel.stateContacts
import com.example.coronatracker.dataClasses.indiaModel.indiaStates
import retrofit2.Call

interface methods {
    @get:GET("covid-19/countries")
    val data: Call<List<Root?>>

    @get:GET("all?yesterday")
    val world: Call<world?>?

    @get:GET("covid19-in/contacts")
    val contacts: Call<stateContacts?>?

    @get:GET("covid19-in/stats/latest")
    val states: Call<indiaStates?>?
}