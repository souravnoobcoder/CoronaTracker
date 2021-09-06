package com.example.coronatracker.Activities;

import static com.example.coronatracker.DataClasses.values.COUNTRY_INTENT;
import static com.example.coronatracker.DataClasses.values.COUNTRY_VAL;
import static com.example.coronatracker.Fragments.countriesData.ARG_PARAM1;
import static com.example.coronatracker.R.string.navigation_drawer_close;
import static com.example.coronatracker.R.string.navigation_drawer_open;
import static com.example.coronatracker.R.string.state_cont_key;
import static com.example.coronatracker.R.string.state_key;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.coronatracker.Api.methods;
import com.example.coronatracker.Api.newApi;
import com.example.coronatracker.DataClasses.Root;
import com.example.coronatracker.DataClasses.indiaContactModel.stateContacts;
import com.example.coronatracker.DataClasses.indiaModel.Regional;
import com.example.coronatracker.DataClasses.indiaModel.indiaStates;
import com.example.coronatracker.DataClasses.world;
import com.example.coronatracker.Fragments.Launching;
import com.example.coronatracker.Fragments.countriesData;
import com.example.coronatracker.Fragments.indiaStateFragment;
import com.example.coronatracker.R;
import com.example.coronatracker.Room.indiaStateModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        , Toolbar.OnMenuItemClickListener {
    public static final String TAGO = "hello sir";
    boolean expanded = false, pressedOnce =false,country=true;
    AppBarLayout box;
    TextView totalPopulation, confirmed, recovered, deaths,
            casesToday, activeCases, deathsToday, criticalCases, casesPerMillion, deathsPerMillion, viewMore;
    LinearLayout moreDataLayout;
    private DrawerLayout drawer;
    List<Root> rootList;
    List<Regional> states;
    List<com.example.coronatracker.DataClasses.indiaContactModel.Regional> contacts;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeValues();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();

        drawer = findViewById(R.id.drawer_Layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                navigation_drawer_open, navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        methods worldM=newApi.getWorld().create(methods.class);
        worldM.getWorld().enqueue(new Callback<world>() {
            @Override
            public void onResponse(@NonNull Call<world> call, @NonNull Response<world> response) {
                world w=response.body();
                assert w != null;
                setWorld(String.valueOf(w.population),String.valueOf(w.cases),String.valueOf(w.recovered)
                        ,String.valueOf(w.deaths),String.valueOf(w.todayCases),String.valueOf(w.active),
                        String.valueOf(w.todayDeaths),String.valueOf(w.critical),String.valueOf(w.casesPerOneMillion)
                        ,String.valueOf(w.deathsPerOneMillion));
            }

            @Override
            public void onFailure(@NonNull Call<world> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Unable to load Data", Toast.LENGTH_SHORT).show();

            }
        });
        setCountries();

        toolbar.setOnMenuItemClickListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.india_info) {
                country=false;
                startIndianState();
            }
             else if (itemId == R.id.world_info) {
                 country=true;
                setCountries();
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        else if (pressedOnce){
            super.onBackPressed();
            return;
        }
        this.pressedOnce =true;
        makeToast("Double Click for Exit");
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    pressedOnce =false;
                }
            },2000);

    }

    public void toolClick(View view) {
        if (expanded) {
            TransitionManager.beginDelayedTransition(box, new AutoTransition());
            moreDataLayout.setVisibility(View.GONE);
            viewMore.setVisibility(View.VISIBLE);
            expanded = false;
        } else {
            TransitionManager.beginDelayedTransition(box, new AutoTransition());
            viewMore.setVisibility(View.GONE);
            moreDataLayout.setVisibility(View.VISIBLE);
            expanded = true;
        }
    }

    private void setWorld(String totalPopulation, String confirmed, String recovered, String deaths,
                          String casesToday, String activeCases, String deathsToday, String criticalCases, String casesPerMillion
            , String deathsPerMillion) {
        new Handler().post(() -> {
            MainActivity.this.totalPopulation.setText(totalPopulation);
            MainActivity.this.confirmed.setText(confirmed);
            MainActivity.this.recovered.setText(recovered);
            MainActivity.this.deaths.setText(deaths);
            MainActivity.this.casesToday.setText(casesToday);
            MainActivity.this.activeCases.setText(activeCases);
            MainActivity.this.deathsToday.setText(deathsToday);
            MainActivity.this.criticalCases.setText(criticalCases);
            MainActivity.this.casesPerMillion.setText(casesPerMillion);
            MainActivity.this.deathsPerMillion.setText(deathsPerMillion);
        });

    }

    private void initializeValues() {
        settingStart();
        viewMore = findViewById(R.id.viewMoreText);
        moreDataLayout = findViewById(R.id.moreDataLayout);
        box = findViewById(R.id.app_bar_layout);
        totalPopulation = findViewById(R.id.total_populationNumber);
        confirmed = findViewById(R.id.confirmedCaseTextView);
        recovered = findViewById(R.id.recoveredTextView);
        deaths = findViewById(R.id.deathsTextView);
        casesToday = findViewById(R.id.casesToday_expandedCard);
        activeCases = findViewById(R.id.activeCases_expandedCard);
        deathsToday = findViewById(R.id.deathsToday_expandedCard);
        criticalCases = findViewById(R.id.criticalCases_expandedCard);
        casesPerMillion = findViewById(R.id.casesPerMillion_expandedCard);
        deathsPerMillion = findViewById(R.id.deathsPerMillion_expandedCard);
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
    }

    void setFragment(List<Root> root) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) root);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide,R.anim.slide)
                .replace(R.id.recycle_fragment, countriesData.class, args)
                .commit();
    }

    void settingStart() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(false)
                .replace(R.id.recycle_fragment, Launching.class, null)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search) {
            Intent intent = new Intent(MainActivity.this, SearchHandle.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(COUNTRY_VAL, (ArrayList<? extends Parcelable>) rootList);
            intent.putExtra(COUNTRY_INTENT, "country");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (itemId == R.id.call) {
            startStateSearch();
        } else if (itemId == R.id.india_states) {
                startIndianState();
        } else if (itemId == R.id.safety) {
            makeToast("Safety");
        } else if (itemId == R.id.second || itemId == R.id.third) {
            makeToast("progress");
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void makeToast(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }

    public void setStateFragment(List<Regional> states,
            List<com.example.coronatracker.DataClasses.indiaContactModel.Regional> contacts) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(getString(state_key), (ArrayList<? extends Parcelable>) states);
        args.putParcelableArrayList(getString(state_cont_key), (ArrayList<? extends Parcelable>) contacts);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide,R.anim.slide)
                .replace(R.id.recycle_fragment, indiaStateFragment.class, args)
                .commit();
    }

    void startIndianState() {

        methods myMethod = newApi.getIndiaState().create(methods.class);
        methods mySecondMethod = newApi.getIndiaState().create(methods.class);

        myMethod.getContacts().enqueue(new Callback<stateContacts>() {
            @Override
            public void onResponse(@NonNull Call<stateContacts> call, @NonNull Response<stateContacts> response) {
                contacts = response.body().data.contacts.regional;
            }

            @Override
            public void onFailure(@NonNull Call<stateContacts> call, @NonNull Throwable t) {
                makeToast("Failed to Get States Contacts");
            }
        });
        mySecondMethod.getStates().enqueue(new Callback<indiaStates>() {
            @Override
            public void onResponse(@NonNull Call<indiaStates> call, @NonNull Response<indiaStates> response) {
                states = response.body().data.regional;
                setStateFragment(states, contacts);
            }

            @Override
            public void onFailure(@NonNull Call<indiaStates> call, @NonNull Throwable t) {
                makeToast("Failed to Get States");
            }
        });
    }
    void setCountries(){
        methods method = newApi.getApiInstance().create(methods.class);
        method.getData().enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(@NonNull Call<List<Root>> call, @NonNull Response<List<Root>> response) {
                assert response.body() != null;
                setFragment(response.body());
                rootList = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<List<Root>> call, @NonNull Throwable t) {
                makeToast("Unable to load Data");
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.search_bar_option) {
            if (country)
            startCountrySearch();
            else startStateSearch();
        }
        return true;
    }

    void startStateSearch() {
        Intent intent = new Intent(MainActivity.this, SearchHandle.class);
        intent.putExtra(COUNTRY_INTENT, "state");
        startActivity(intent);
    }
    void startCountrySearch(){
        Intent intent = new Intent(MainActivity.this, SearchHandle.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(COUNTRY_VAL, (ArrayList<? extends Parcelable>) rootList);
        intent.putExtras(bundle);
        intent.putExtra(COUNTRY_INTENT, "country");
        startActivity(intent);
    }

}