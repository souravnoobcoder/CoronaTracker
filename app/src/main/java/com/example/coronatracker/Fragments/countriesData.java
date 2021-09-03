package com.example.coronatracker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coronatracker.Adapters.countryAdapter;
import com.example.coronatracker.DataClasses.Root;
import com.example.coronatracker.R;

import java.util.ArrayList;

public class countriesData extends Fragment {

    public static final String ARG_PARAM1 = "param1";
    static ArrayList<Root> DATA;
    countryAdapter adapter;

    public countriesData() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           DATA = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
        adapter=new countryAdapter(DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.country_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = requireView().findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}