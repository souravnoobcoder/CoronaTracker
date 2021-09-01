package com.example.coronatracker.Api;

import com.example.coronatracker.DataClasses.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface methods {
    @GET("covid-19/countries")
    Call<List<Root>> getData();
}
