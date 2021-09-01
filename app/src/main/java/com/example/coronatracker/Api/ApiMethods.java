package com.example.coronatracker.Api;


import retrofit2.http.GET;

public interface ApiMethods {

    @GET("summary")
    void mai();
}
