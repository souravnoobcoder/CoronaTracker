package com.example.coronatracker;

import static com.example.coronatracker.Fragments.BlankFragment.ARG_PARAM1;

import android.os.Bundle;
import android.os.Parcelable;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
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
import androidx.fragment.app.FragmentContainerView;

import com.example.coronatracker.Api.methods;
import com.example.coronatracker.Api.newApi;
import com.example.coronatracker.DataClasses.Root;
import com.example.coronatracker.DataClasses.world;
import com.example.coronatracker.Fragments.BlankFragment;
import com.example.coronatracker.Fragments.Launching;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    boolean expanded = false;
    AppBarLayout box;
    TextView totalPopulation, confirmed, recovered, deaths,
            casesToday, activeCases, deathsToday, criticalCases, casesPerMillion, deathsPerMillion, viewMore;
    LinearLayout moreDataLayout;
    FragmentContainerView startUp;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeValues();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_Layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        methods method = newApi.getApiInstance().create(methods.class);
        method.getData().enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(@NonNull Call<List<Root>> call, @NonNull Response<List<Root>> response) {
               setFragment(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Root>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Unable to load Data", Toast.LENGTH_SHORT).show();
            }
        });
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
        this.totalPopulation.setText(totalPopulation);
        this.confirmed.setText(confirmed);
        this.recovered.setText(recovered);
        this.deaths.setText(deaths);
        this.casesToday.setText(casesToday);
        this.activeCases.setText(activeCases);
        this.deathsToday.setText(deathsToday);
        this.criticalCases.setText(criticalCases);
        this.casesPerMillion.setText(casesPerMillion);
        this.deathsPerMillion.setText(deathsPerMillion);
    }

    private void initializeValues() {
        startUp=findViewById(R.id.launching);
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

    }
    public void setFragment(List<Root> root) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) root);
        startUp.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("BACK_STACK")
                .replace(R.id.recycle_fragment,BlankFragment.class,args)
                .commit();
    }
    public void settingStart(){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.recycle_fragment, Launching.class,null)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if (itemId == R.id.search) {
            makeToast("Searched");
        } else if (itemId == R.id.call) {
            makeToast("Call");
        } else if (itemId == R.id.india_states) {
            makeToast("India States");
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
}