package com.example.coronatracker.api

import com.example.coronatracker.dataClasses.Root
import retrofit2.http.GET
import com.example.coronatracker.dataClasses.world
import com.example.coronatracker.dataClasses.indiaContactModel.stateContacts
import com.example.coronatracker.dataClasses.indiaModel.indiaStates
import retrofit2.Call

interface Methods {
    @GET("covid-19/countries")
    fun getData() : Call<List<Root?>>

    @GET("all?yesterday")
    fun getWorld(): Call<world?>?

    @GET("covid19-in/contacts")
    fun getContacts(): Call<stateContacts?>?

    @GET("covid19-in/stats/latest")
    fun getStates(): Call<indiaStates?>?
}