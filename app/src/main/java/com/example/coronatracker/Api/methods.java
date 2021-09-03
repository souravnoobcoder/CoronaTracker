package com.example.coronatracker.Api;

import com.example.coronatracker.DataClasses.Root;
import com.example.coronatracker.DataClasses.indianStates;
import com.example.coronatracker.DataClasses.stateContacts;
import com.example.coronatracker.DataClasses.world;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface methods {
    @GET("covid-19/countries")
    Call<List<Root>> getData();

    @GET("all?yesterday")
    Call<world> getWorld();

    @GET("covid19-in/contacts")
    Call<stateContacts> getContacts();

    @GET("covid19-in/stats/latest")
    Call<indianStates> getStates();
}
