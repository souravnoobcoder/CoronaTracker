package com.example.coronatracker.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.Activities.MainActivity;
import com.example.coronatracker.Adapters.stateAdapter;
import com.example.coronatracker.DataClasses.indiaModel.Regional;
import com.example.coronatracker.DataClasses.indiaStateModel;
import com.example.coronatracker.R;

import java.util.ArrayList;
import java.util.List;

public class indiaStateFragment extends Fragment {

    List<Regional> states;
    List<com.example.coronatracker.DataClasses.indiaContactModel.Regional> contacts;
    stateAdapter adapter;

    public indiaStateFragment() {
        // Required empty public constructor
        //contacts.data.contacts.regional.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        states=new ArrayList<>();
        contacts=new ArrayList<>();
        if (getArguments() != null) {
            states = getArguments().getParcelableArrayList(getString(R.string.state_key));
            contacts = getArguments().getParcelableArrayList(getString(R.string.state_cont_key));
        }
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
            adapter=new stateAdapter(createData());
            recyclerView.setAdapter(adapter);
    }

    private List<indiaStateModel> createData() {
        List<indiaStateModel> model = new ArrayList<>();

            for(int i = 0; i < states.size(); i++) {
                Regional state = states.get(i);
                for (int j = 0; j < contacts.size(); j++) {
                    com.example.coronatracker.DataClasses.indiaContactModel.Regional
                            contact = contacts.get(j);
                    String stateNameI = state.loc.toLowerCase();
                    String stateNameJ = contact.loc.toLowerCase();
                    if (stateNameI.equals(stateNameJ)) {
                        contacts.remove(contact);
                        int confirmedCasesForeign = state.confirmedCasesForeign;
                        int discharged = state.discharged;
                        int deaths = state.deaths;
                        int totalConfirmed = state.totalConfirmed;
                        String number = contact.number;
                        int active=totalConfirmed-(deaths+discharged);
                        model.add(new indiaStateModel(stateNameI, confirmedCasesForeign, discharged, deaths,
                                totalConfirmed, number,active));
                    }
                }
            }
        return model;
    }
}