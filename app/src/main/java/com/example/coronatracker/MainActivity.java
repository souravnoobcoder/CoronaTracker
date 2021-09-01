package com.example.coronatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.coronatracker.Adapters.countryAdapter;
import com.example.coronatracker.Api.methods;
import com.example.coronatracker.Api.newApi;
import com.example.coronatracker.DataClasses.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        methods method= newApi.getApiInstance().create(methods.class);
        method.getData().enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(@NonNull Call<List<Root>> call, @NonNull Response<List<Root>> response) {
                recyclerView.setAdapter(new countryAdapter(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<Root>> call, @NonNull Throwable t) {
               t.printStackTrace();
            }
        });

    }
}