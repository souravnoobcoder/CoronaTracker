package com.example.coronatracker.api

import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.dataClasses.StateContacts
import com.example.coronatracker.dataClasses.indiaStates
import com.example.coronatracker.dataClasses.World
import retrofit2.http.GET

interface Methods {


    // fetches countries data from api
    @GET("covid-19/countries")
    fun getCountries(): List<Root>

    // fetches world statistics on corona from api
    @GET("all?yesterday")
    fun getWorld(): World

    // fetches contacts of indian states
    @GET("covid19-in/contacts")
    fun getIndiaStateContacts(): StateContacts

    // fetches indian state data form api
    @GET("covid19-in/stats/latest")
    fun getIndiaStates(): indiaStates
}