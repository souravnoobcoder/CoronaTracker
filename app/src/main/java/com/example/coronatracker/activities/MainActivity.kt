package com.example.coronatracker.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import com.example.coronatracker.R
import com.example.coronatracker.R.string
import com.example.coronatracker.api.Methods
import com.example.coronatracker.api.NewsApi
import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.dataClasses.values
import com.example.coronatracker.databinding.ActivityMainBinding
import com.example.coronatracker.databinding.WorldItemBinding
import com.example.coronatracker.features.TrackViewModel
import com.example.coronatracker.fragments.CountryData
import com.example.coronatracker.fragments.IndiaFragment
import com.example.coronatracker.fragments.Launching
import com.example.coronatracker.funtions.Location
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    Toolbar.OnMenuItemClickListener {

    private val viewModel: TrackViewModel by viewModels()
    private var expanded = false
    private var pressedOnce = false
    var country = true
    private var stateLaunched = false
    var rootList: List<Root?>? = null
    var states: List<Regional?>? = null
    var contacts: List<com.example.coronatracker.dataClasses.indiaContactModel.Regional?>? = null
    private var box: AppBarLayout? = null
    private var drawer: DrawerLayout? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var worldBinding: WorldItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        worldBinding = WorldItemBinding.bind(binding.root)
        /**
         * Setting theme of app by checking in sharedPreferences
         */
        val theme = themeStatus
        if (theme == values.LIGHT) setTheme(R.style.light)
        if (theme == values.DARK) setTheme(R.style.night)
        setTheme(R.style.Theme_CoronaTracker)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(toolbar)
            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.search_bar_option) {
                    if (country) startCountrySearch() else startStateSearch()
                }
                true
            }
            appBarLayout.setOnClickListener{
                if (expanded) {
                    TransitionManager.beginDelayedTransition(appBarLayout, AutoTransition())
                    worldBinding.run {
                        moreDataLayout.visibility = View.GONE
                        viewMore.visibility = View.VISIBLE
                    }
                    expanded = false
                } else {
                    TransitionManager.beginDelayedTransition(appBarLayout, AutoTransition())
                    worldBinding.run {
                        viewMore.visibility = View.GONE
                        moreDataLayout.visibility = View.VISIBLE
                    }
                    expanded = true
                }
            }
            box=appBarLayout

            drawer = drawerLayout
            refreshLayout.run {
                setOnRefreshListener {
                    if (country) setCountries() else startIndianState()
                    CoroutineScope(Main).launch {
                        delay(500)
                        isRefreshing = false
                    }
                }
            }

            bottomNavigationView.setOnItemSelectedListener {
                val itemId = it.itemId
                if (itemId == R.id.india_info) {
                    country = false
                    if (!stateLaunched) {
                        startIndianState()
                        stateLaunched = true
                    } else {
                        setStateFragment(states, contacts)
                    }
                } else if (itemId == R.id.world_info) {
                    country = true
                    setCountryFragment(rootList)
                }
                true
            }
        }
        viewModel.world.observe(this@MainActivity) { world ->
            val data = world.data
            worldBinding.run {
                totalPopulation.text = data?.totalPopulation
                confirmedCase.text = data?.confirmed
                recovered.text = data?.recovered
                deaths.text = data?.deaths
                casesToday.text = data?.casesToday
                activeCases.text = data?.activeCases
                deathsToday.text = data?.deathsToday
                criticalCases.text = data?.criticalCases
                casesPerMillion.text = data?.casesPerMillion
                deathsPerMillion.text = data?.deathsPerMillion
            }
        }

        // Getting app version
        val appVersion = this.packageManager.getPackageInfo(packageName, 0).versionName
        print("verion of app : $appVersion")
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.showOverflowMenu()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            string.navigation_drawer_open, string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()

        setCountries()
        toolbar.setOnMenuItemClickListener(this)

        val location = Location(this)
        val locationData = location.getLocation()
        print(" country : ${locationData.country}  state : ${locationData.state}")
        makeToast(" country : ${locationData.country}  state : ${locationData.state}")
    }

    /**
     * If our drawer is opened then it first closes our drawer and then back only on double click
     * between 2 seconds gap
     */
    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
            return
        } else if (pressedOnce) {
            super.onBackPressed()
            return
        }
        pressedOnce = true
        makeToast("Double Click for Exit")
        Handler(Looper.getMainLooper()).postDelayed({ pressedOnce = false }, 2000)
    }

    /**
     * Handle click on world information which is showing in appBarLayout
     * It makes extra information visible by setting
     * [(( moreDataLayout!!.visibility = View.VISIBLE )) , (( moreDataLayout!!.visibility = View.GONE ))] when expanded is false
     * And makes viewMore visibility Gone by setting [(( moreDataLayout!!.visibility = View.VISIBLE )) , (( viewMore!!.visibility = View.GONE ))]
     */


    /**
     * Setting world information in appBarLayout
     */


    /**
     * Launching country fragment and passing  list of countries
     * with animation
     */
    fun setCountryFragment(countries: List<Root?>?) {
        val args = Bundle()
        args.putParcelableArrayList(
            CountryData.ARG_PARAM1,
            countries as ArrayList<out Parcelable?>?
        )
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_left,
                R.anim.exit_to_left
            ) // enter    exit   pop enter pop exit
            .replace(R.id.recycle_fragment_view, CountryData::class.java, args)
            .commitAllowingStateLoss()
    }

    /**
     * Launching state fragment and list of contacts and states
     * with animation
     */
    fun setStateFragment(
        states: List<Regional?>?,
        contacts: List<com.example.coronatracker.dataClasses.indiaContactModel.Regional?>?
    ) {
        val args = Bundle()
        args.putParcelableArrayList(
            getString(string.state_key),
            states as ArrayList<out Parcelable?>?
        )
        args.putParcelableArrayList(
            getString(string.state_cont_key),
            contacts as ArrayList<out Parcelable?>?
        )
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
            .replace(R.id.recycle_fragment_view, IndiaFragment::class.java, args)
            .commit()
    }

    /**
     * Launches Loading fragment
     */
    private fun settingStart() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(false)
            .replace(R.id.recycle_fragment_view, Launching::class.java, null)
            .commit()
    }

    /**
     * Handling events of Navigation Drawer
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.search) makeToast("search")
        else if (itemId == R.id.call) makeToast("call")
        else if (itemId == R.id.change_theme) makeAlert()
        else if (itemId == R.id.safety) {
            throw RuntimeException("Test Crash")
        } else if (itemId == R.id.second || itemId == R.id.third) makeToast("progress")
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    fun makeToast(message: String?) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }

    /**
     * fetches states and contacts data from api and give this data to setStateFragment() function which launches fragment
     */
    private fun startIndianState() {
        settingStart();
        val myMethod = NewsApi.indiaState?.create(Methods::class.java)
        if (myMethod != null) {
            myMethod.getIndiaStateContacts()?.enqueue(object : Callback<stateContacts?> {
                override fun onResponse(
                    call: Call<stateContacts?>,
                    response: Response<stateContacts?>
                ) {
                    assert(response.body() != null)
                    contacts = response.body()!!.data.contacts.regional

                }

                override fun onFailure(call: Call<stateContacts?>, t: Throwable) {
                    makeToast("Failed to Get States Contacts")
                }
            })
            myMethod.getIndiaStates()?.enqueue(object : Callback<indiaStates?> {
                override fun onResponse(
                    call: Call<indiaStates?>,
                    response: Response<indiaStates?>
                ) {
                    assert(response.body() != null)
                    states = response.body()!!.data.regional
                    setStateFragment(states, contacts)
                }

                override fun onFailure(call: Call<indiaStates?>, t: Throwable) {
                    makeToast("Failed to Get States")
                }
            })
        }

    }

    /**
     * fetches country data from api and give this data to setCountry() function which launches fragment
     */
    private fun setCountries() {
        val method = NewsApi.apiInstance?.create(Methods::class.java)
        method?.getCountries()?.enqueue(object : Callback<List<Root?>?> {
            override fun onResponse(call: Call<List<Root?>?>, response: Response<List<Root?>?>) {
                assert(response.body() != null)
                setCountryFragment(response.body())
                rootList = response.body()
            }

            override fun onFailure(call: Call<List<Root?>?>, t: Throwable) {
                makeToast("Unable to load Data")
            }
        })
    }

    /**
     * Initializing search icon which is on the AppBarLayout
     */
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /**
     * Initializing and setting action when search icon is pressed in appBarLayout
     * and country fragment  is opened it invokes startCountrySearch() method
     * and if state fragment  is opened it invokes startStateSearch() method
     */
    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.search_bar_option) {
            if (country) startCountrySearch() else startStateSearch()
        }
        return true
    }

    /**
     * @Launching search activity for state search
     * by passing value state
     */
    private fun startStateSearch() {
        val intent = Intent(this@MainActivity, SearchHandle::class.java)
        intent.putExtra(values.COUNTRY_INTENT, "state")
        startActivity(intent)
    }

    /**
     * @Launching search activity for country search
     * by passing value country
     */
    private fun startCountrySearch() {
        val intent = Intent(this@MainActivity, SearchHandle::class.java)
        intent.putParcelableArrayListExtra(
            values.COUNTRY_VAL,
            rootList as ArrayList<out Parcelable?>?
        )
        intent.putExtra(values.COUNTRY_INTENT, "country")
        startActivity(intent)
    }

    /**
     * on Refresh function is invoked whenever user refreshed the page
     * Firstly it checks on which fragment user is and then user is in
     * country fragment it calls setCountries() method and when user is in
     * state fragment it calls startIndianState(
     */

    /**
     * @Showing AlertDialog Box for setting theme of the app
     * when any theme is selected it updates the sharePreference of application
     */
    private fun makeAlert() {
        val themeView = layoutInflater.inflate(R.layout.theme_choice, null)
        val dark = themeView.findViewById<RadioButton>(R.id.dark_theme)
        val light = themeView.findViewById<RadioButton>(R.id.light_theme)
        val defaultTheme = themeView.findViewById<RadioButton>(R.id.default_theme)
        if (themeStatus == values.LIGHT) light.isChecked = true
        if (themeStatus == values.DARK) dark.isChecked = true else defaultTheme.isChecked = true
        setContentView(R.layout.activity_main)
        val dialog = AlertDialog.Builder(this)
            .setView(themeView)
            .setTitle("Select Theme")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                themeStatus =
                    if (light.isChecked) values.LIGHT else if (dark.isChecked) values.DARK else values.DEFAULT
            }
            .create()
        dialog.show()
    }

    /**
     * getting and setting the themeStatus in sharedPreferences
     * and while setting themeStatus also relaunching activity for applying changes
     */
    private var themeStatus: String?
        get() {
            val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(applicationContext)
            return sharedPreferences.getString(values.NEW_KEY, values.LIGHT)
        }
        set(mode) {
            val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = sharedPreferences.edit()
            editor.putString(values.NEW_KEY, mode)
            editor.apply()
            finish()
            startActivity(intent)
        }

    companion object {
        const val TAGO = "hello sir"
    }
}