package com.example.coronatracker.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import androidx.preference.PreferenceManager
import com.example.coronatracker.R
import com.example.coronatracker.R.string
import com.example.coronatracker.dataClasses.Constants
import com.example.coronatracker.databinding.ActivityMainBinding
import com.example.coronatracker.databinding.WorldItemBinding
import com.example.coronatracker.features.TrackViewModel
import com.example.coronatracker.fragments.CountryData
import com.example.coronatracker.fragments.IndiaFragment
import com.example.coronatracker.funtions.Location
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
     {

    private val viewModel: TrackViewModel by viewModels()
    private var expanded = false
    private var pressedOnce = false
    var country = true
    private lateinit var fragmentContainer : FragmentContainerView
    private var box: AppBarLayout? = null
    private var drawer: DrawerLayout? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var worldBinding: WorldItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        worldBinding = binding.world
        /**
         * Setting theme of app by checking in sharedPreferences
         */
        val theme = themeStatus
        if (theme == Constants.LIGHT) setTheme(R.style.light)
        if (theme == Constants.DARK) setTheme(R.style.night)
        setTheme(R.style.Theme_CoronaTracker)
        setContentView(binding.root)

        binding.apply {

            fragmentContainer=recycleFragmentView
            setSupportActionBar(toolbar)
            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.search_bar_option) {
                    if (country) launchCountrySearch() else launchIndiaSearch()
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
                    if (country) launchCountryFragment() else launchIndiaFragment()
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
                   launchIndiaFragment()
                } else if (itemId == R.id.world_info) {
                    country = true
                    launchCountryFragment()
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

        launchCountryFragment()
       // toolbar.setOnMenuItemClickListener(this)
//
//        val location = Location(this)
//        val locationData = location.getLocation()
//        print(" country : ${locationData.country}  state : ${locationData.state}")
//        makeToast(" country : ${locationData.country}  state : ${locationData.state}")
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
     * Launching country fragment and passing  list of countries
     * with animation
     */
    private fun launchCountryFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_left,
                R.anim.exit_to_left
            ) // enter    exit   pop enter pop exit
            .replace(fragmentContainer.id,CountryData::class.java,null)
            .commitAllowingStateLoss()
    }

    /**
     * Launching state fragment and list of contacts and states
     * with animation
     */
    private fun launchIndiaFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
            .replace(fragmentContainer.id, IndiaFragment::class.java,null)
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

    private fun makeToast(message: String?) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
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
//    override fun onMenuItemClick(item: MenuItem): Boolean {
//        if (item.itemId == R.id.search_bar_option) {
//            if (country) launchCountrySearch() else launchIndiaSearch()
//        }
//        return true
//    }

    /**
     * @Launching search activity for state search
     */
    private fun launchIndiaSearch() {
        val intent = Intent(this@MainActivity, StateSearch::class.java)
        startActivity(intent)
    }

    /**
     * @Launching search activity for country search
     */
    private fun launchCountrySearch() {
        val intent = Intent(this@MainActivity, StateSearch::class.java)
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
        if (themeStatus == Constants.LIGHT) light.isChecked = true
        if (themeStatus == Constants.DARK) dark.isChecked = true else defaultTheme.isChecked = true
        setContentView(R.layout.activity_main)
        val dialog = AlertDialog.Builder(this)
            .setView(themeView)
            .setTitle("Select Theme")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                themeStatus =
                    if (light.isChecked) Constants.LIGHT else if (dark.isChecked) Constants.DARK else Constants.DEFAULT
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
            return sharedPreferences.getString(Constants.NEW_KEY, Constants.LIGHT)
        }
        set(mode) {
            val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = sharedPreferences.edit()
            editor.putString(Constants.NEW_KEY, mode)
            editor.apply()
            finish()
            startActivity(intent)
        }

    companion object {
        const val TAGO = "hello sir"
    }
}