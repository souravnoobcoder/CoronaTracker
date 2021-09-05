package com.example.coronatracker.Api;

import com.example.coronatracker.DataClasses.Root;
import com.example.coronatracker.DataClasses.indiaContactModel.stateContacts;
import com.example.coronatracker.DataClasses.indiaModel.indiaStates;
import com.example.coronatracker.DataClasses.world;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface methods {
    @GET("covid-19/countries")
    Call<List<Root>> getData();

    @GET("all?yesterday")
    Call<world> getWorld();

    @GET("covid19-in/contacts")
    Call<stateContacts> getContacts();

    @GET("covid19-in/stats/latest")
    Call<indiaStates> getStates();
}
