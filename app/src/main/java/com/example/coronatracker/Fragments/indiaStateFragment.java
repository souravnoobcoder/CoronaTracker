package com.example.coronatracker.Fragments;

import static com.example.coronatracker.Activities.MainActivity.TAGO;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.Adapters.stateAdapter;
import com.example.coronatracker.DataClasses.indiaModel.Regional;
import com.example.coronatracker.R;
import com.example.coronatracker.Room.indiaStateModel;
import com.example.coronatracker.Room.viewModel;

import java.util.ArrayList;
import java.util.List;

public class indiaStateFragment extends Fragment {
    viewModel myModel;
    List<Regional> states;
    List<com.example.coronatracker.DataClasses.indiaContactModel.Regional> contacts;
    stateAdapter adapter;
    List<indiaStateModel> data;
    private LifecycleOwner mViewLifecycleOwner;

    public indiaStateFragment() {
        // Required empty public constructor
        //contacts.data.contacts.regional.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myModel = ViewModelProviders.of(requireActivity()).get(viewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_india_state, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        states = new ArrayList<>();
        contacts = new ArrayList<>();
        data = new ArrayList<>();
        if (getArguments() != null) {
            states = getArguments().getParcelableArrayList(getString(R.string.state_key));
            contacts = getArguments().getParcelableArrayList(getString(R.string.state_cont_key));
        } else {
            System.out.println(TAGO + "arguments are zero");
            Log.v(TAGO, "arguments are zero");
        }
        if (states == null || states.isEmpty() || contacts == null || contacts.isEmpty()) {
            System.out.println(TAGO + "states or contacts is null");
            Log.v(TAGO, "states or contacts is null");
        }

        RecyclerView recyclerView = requireView().findViewById(R.id.stateRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new Handler().post(() -> {
            data = createData();
            if (!(data.isEmpty())) {
                adapter = new stateAdapter(data);
                recyclerView.setAdapter(adapter);
                setRoom();
            } else {
                myModel.getOfflineData().observeForever(indiaStateModels -> {
                    adapter = new stateAdapter(indiaStateModels);
                    recyclerView.setAdapter(adapter);
                });
                System.out.println(TAGO + "data size is zero");
                Log.v(TAGO, "data size is zero");
            }
        });
    }

    private List<indiaStateModel> createData() {
        List<indiaStateModel> model = new ArrayList<>();
        if (states != null && contacts != null) {
            if (states.isEmpty() || contacts.isEmpty()) {
                System.out.println(TAGO + "value inside");
                Log.v(TAGO, "value inside");
            }
            for (int i = 0; i < states.size(); i++) {
                Regional state = states.get(i);
                for (int j = 0; j < contacts.size(); j++) {
                    com.example.coronatracker.DataClasses.indiaContactModel.Regional
                            contact = contacts.get(j);
                    String stateNameI = state.loc;
                    String stateNameJ = contact.loc;
                    if (stateNameI.equals(stateNameJ)) {
                        contacts.remove(contact);
                        int confirmedCasesForeign = state.confirmedCasesForeign;
                        int discharged = state.discharged;
                        int deaths = state.deaths;
                        int totalConfirmed = state.totalConfirmed;
                        String number = contact.number;
                        int active = totalConfirmed - (deaths + discharged);
                        model.add(new indiaStateModel(stateNameI, confirmedCasesForeign, discharged, deaths,
                                totalConfirmed, number, active));
                    }
                }
            }
        }
        if (model.isEmpty())
            System.out.println("googogogogo");
        else System.out.println("googogogogooooooooooo");
        return model;
    }

    void sortArray() {

    }

    void setRoom() {
        myModel.deleteAll();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < data.size(); i++) {
            myModel.insert(data.get(i));
        }
    }
}