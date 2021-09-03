package com.example.coronatracker.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.Adapters.countryAdapter;
import com.example.coronatracker.DataClasses.Root;
import com.example.coronatracker.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchHandle extends AppCompatActivity {
    TextInputEditText inputEditText;
    private List<Root> fullSearchingList;
    countryAdapter adapter;
    TextWatcher textWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_handle);

        inputEditText = findViewById(R.id.search_bar);
        adapter=new countryAdapter(null);
        fullSearchingList = getIntent().getExtras().getParcelableArrayList(getString(R.string.intent_search));
        RecyclerView recyclerView = findViewById(R.id.searched_countries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
         textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.update(sorting(s.toString().toLowerCase()));
            }
        };
        inputEditText.addTextChangedListener(textWatcher);
        super.onStart();
    }

    @Override
    protected void onStop() {
        inputEditText.removeTextChangedListener(textWatcher);
        super.onStop();
    }

    List<Root> sorting(String searched) {
        List<Root> searchingSortedList = new ArrayList<>();
        for (Root root : fullSearchingList) {
            if (root.country.toLowerCase().contains(searched))
                searchingSortedList.add(root);
        }
        return searchingSortedList;
    }
}