package com.example.coronatracker.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.Adapters.stateAdapter;
import com.example.coronatracker.DataClasses.indiaModel.Data;
import com.example.coronatracker.DataClasses.indiaModel.indiaS;
import com.example.coronatracker.DataClasses.indiaStateModel;
import com.example.coronatracker.DataClasses.indianStates;
import com.example.coronatracker.DataClasses.stateContacts;
import com.example.coronatracker.R;

import java.util.ArrayList;
import java.util.List;

public class indiaStateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    static indianStates states;
    static stateContacts contacts;
    stateAdapter adapter;


    public indiaStateFragment() {
        // Required empty public constructor
        //contacts.data.contacts.regional.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            states = (indianStates) getArguments().getSerializable(getString(R.string.state_key));
            contacts = (stateContacts) getArguments().getSerializable(getString(R.string.state_cont_key));
        }
        adapter = new stateAdapter(createData(states,contacts));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_india_state, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = requireView().findViewById(R.id.stateRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private List<indiaStateModel> createData(indianStates states, stateContacts contacts) {
        List<indiaStateModel> model = new ArrayList<>();
        for (int i = 0; i < states.data.regional.size(); i++) {
            for (int j = 0; j < contacts.data.contacts.regional.size(); i++) {
                String stateNameI = states.data.regional.get(i).loc.toLowerCase();
                String stateNameJ = contacts.data.contacts.regional.get(j).loc.toLowerCase();
                if (stateNameI.equals(stateNameJ)) {
                    int confirmedCasesForeign=states.data.regional.get(i).confirmedCasesForeign;
                    int discharged=states.data.regional.get(i).discharged;
                    int deaths=states.data.regional.get(i).deaths;
                    int totalConfirmed=states.data.regional.get(i).totalConfirmed;
                    String number=contacts.data.contacts.regional.get(j).number;
                    model.add(new indiaStateModel(stateNameI,confirmedCasesForeign,discharged,deaths,totalConfirmed,number));
                }
            }
        }
        return model;
    }
}