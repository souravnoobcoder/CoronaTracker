package com.example.coronatracker.Activities;

import static com.example.coronatracker.DataClasses.values.COUNTRY_INTENT;
import static com.example.coronatracker.DataClasses.values.COUNTRY_VAL;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.Adapters.countryAdapter;
import com.example.coronatracker.Adapters.stateAdapter;
import com.example.coronatracker.DataClasses.Root;
import com.example.coronatracker.R;
import com.example.coronatracker.Room.database;
import com.example.coronatracker.Room.indiaStateModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchHandle extends AppCompatActivity {
    TextInputEditText inputEditText;
    String know;
    private List<Root> fullSearchingList;
    private List<indiaStateModel> fullSearchingListState;
    countryAdapter adapter;
    stateAdapter adapterS;
    TextWatcher textWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_handle);

        fullSearchingListState=new ArrayList<>();
        fullSearchingList=new ArrayList<>();
        inputEditText = findViewById(R.id.search_bar);
        RecyclerView recyclerView = findViewById(R.id.searched_countries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        know=getIntent().getStringExtra(COUNTRY_INTENT);
        if (know.equals("country")){
            inputEditText.setHint("Enter Country");
            adapter=new countryAdapter(null);
            fullSearchingList = getIntent().getExtras().getParcelableArrayList(COUNTRY_VAL);
            recyclerView.setAdapter(adapter);
        }else {
            inputEditText.setHint("Enter state");
            adapterS = new stateAdapter(null);
            recyclerView.setAdapter(adapterS);
            setStateList();
        }
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
                if (know.equals("country"))
                adapter.update(sorting(s.toString().toLowerCase()));
                else adapterS.update(sortingOfState(s.toString().toLowerCase()));
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
    List<indiaStateModel> sortingOfState(String searched){
        List<indiaStateModel> sortedList=new ArrayList<>();
        for (int i = 0; i < 34; i++) {
            if (fullSearchingListState.get(i).getLoc().toLowerCase().contains(searched))
                sortedList.add(fullSearchingListState.get(i));
        }
//        for (indiaStateModel unSort :fullSearchingListState) {
//            if (unSort.getLoc().toLowerCase().contains(searched))
//                sortedList.add(unSort);
//        }
        return sortedList;
    }

    void setStateList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fullSearchingListState = database.getDbINSTANCE(getApplicationContext()).contactDao().getOfflineDataB();

            }
        }).start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}