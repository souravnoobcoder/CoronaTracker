package com.example.coronatracker.activities

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.appbar.AppBarLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.widget.LinearLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import android.os.Bundle
import com.example.coronatracker.dataClasses.values
import com.example.coronatracker.R
import com.example.coronatracker.R.string
import com.example.coronatracker.api.Methods
import com.example.coronatracker.api.NewsApi
import com.example.coronatracker.dataClasses.world
import android.widget.Toast
import androidx.core.view.GravityCompat
import android.os.Looper
import com.example.coronatracker.fragments.CountryData
import android.os.Parcelable
import com.example.coronatracker.fragments.IndiaStateFragment
import com.example.coronatracker.fragments.Launching
import com.example.coronatracker.dataClasses.indiaContactModel.stateContacts
import com.example.coronatracker.dataClasses.indiaModel.indiaStates
import android.content.Intent
import android.widget.RadioButton
import android.os.Handler
import android.preference.PreferenceManager
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.dataClasses.indiaModel.Regional
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    Toolbar.OnMenuItemClickListener, OnRefreshListener {
    private var expanded = false
    private var pressedOnce = false
    var country = true
    private var stateLaunched = false
    private var totalPopulation: TextView? = null
    var confirmed: TextView? = null
    var recovered: TextView? = null
    var deaths: TextView? = null
    private var casesToday: TextView? = null
    private var activeCases: TextView? = null
    private var deathsToday: TextView? = null
    private var criticalCases: TextView? = null
    private var casesPerMillion: TextView? = null
    private var deathsPerMillion: TextView? = null
    private var viewMore: TextView? = null
    var rootList: List<Root?>? = null
    var states: List<Regional?>? = null
    var contacts: List<com.example.coronatracker.dataClasses.indiaContactModel.Regional?>? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private var box: AppBarLayout? = null
    var layout: SwipeRefreshLayout? = null
    private var moreDataLayout: LinearLayout? = null
    private var drawer: DrawerLayout? = null
    private var fragmentContainerView: FragmentContainerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = themeStatus
        if (theme == values.LIGHT) {
            makeToast(theme)
            setTheme(R.style.light)
        }
        if (theme == values.DARK) {
            makeToast(theme)
            setTheme(R.style.night)
        } else {
            makeToast(theme)
            setTheme(R.style.Theme_CoronaTracker)
        }
        setContentView(R.layout.activity_main)
        initializeValues()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.showOverflowMenu()
        drawer = findViewById(R.id.drawer_Layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            string.navigation_drawer_open, string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()
        val worldM = NewsApi.world?.create(Methods::class.java)
            worldM?.getWorld()?.enqueue(object : Callback<world?> {
                override fun onResponse(call: Call<world?>, response: Response<world?>) {
                    val w = response.body()!!
                    setWorld(
                        w.population.toString(),
                        w.cases.toString(),
                        w.recovered.toString(),
                        w.deaths.toString(),
                        w.todayCases.toString(),
                        w.active.toString(),
                        w.todayDeaths.toString(),
                        w.critical.toString(),
                        w.casesPerOneMillion.toString(),
                        w.deathsPerOneMillion.toString()
                    )
                }

                override fun onFailure(call: Call<world?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Unable to load Data", Toast.LENGTH_SHORT).show()
                }
            })
        setCountries()
        toolbar.setOnMenuItemClickListener(this)
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item: MenuItem ->
            val itemId = item.itemId
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
        layout!!.setOnRefreshListener(this)
    }

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

    fun toolClick(view: View) {
        if (expanded) {
            TransitionManager.beginDelayedTransition(box, AutoTransition())
            moreDataLayout!!.visibility = View.GONE
            viewMore!!.visibility = View.VISIBLE
            expanded = false
        } else {
            TransitionManager.beginDelayedTransition(box, AutoTransition())
            viewMore!!.visibility = View.GONE
            moreDataLayout!!.visibility = View.VISIBLE
            expanded = true
        }
    }

    private fun setWorld(
        totalPopulation: String,
        confirmed: String,
        recovered: String,
        deaths: String,
        casesToday: String,
        activeCases: String,
        deathsToday: String,
        criticalCases: String,
        casesPerMillion: String,
        deathsPerMillion: String
    ) {
        Handler().post {
            this@MainActivity.totalPopulation!!.text = totalPopulation
            this@MainActivity.confirmed!!.text = confirmed
            this@MainActivity.recovered!!.text = recovered
            this@MainActivity.deaths!!.text = deaths
            this@MainActivity.casesToday!!.text = casesToday
            this@MainActivity.activeCases!!.text = activeCases
            this@MainActivity.deathsToday!!.text = deathsToday
            this@MainActivity.criticalCases!!.text = criticalCases
            this@MainActivity.casesPerMillion!!.text = casesPerMillion
            this@MainActivity.deathsPerMillion!!.text = deathsPerMillion
        }
    }

    private fun initializeValues() {
        settingStart()
        layout = findViewById(R.id.refresh_layout)
        viewMore = findViewById(R.id.viewMoreText)
        moreDataLayout = findViewById(R.id.moreDataLayout)
        box = findViewById(R.id.app_bar_layout)
        totalPopulation = findViewById(R.id.total_populationNumber)
        confirmed = findViewById(R.id.confirmedCaseTextView)
        recovered = findViewById(R.id.recoveredTextView)
        deaths = findViewById(R.id.deathsTextView)
        casesToday = findViewById(R.id.casesToday_expandedCard)
        activeCases = findViewById(R.id.activeCases_expandedCard)
        deathsToday = findViewById(R.id.deathsToday_expandedCard)
        criticalCases = findViewById(R.id.criticalCases_expandedCard)
        casesPerMillion = findViewById(R.id.casesPerMillion_expandedCard)
        deathsPerMillion = findViewById(R.id.deathsPerMillion_expandedCard)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        fragmentContainerView = findViewById(R.id.recycle_fragment_view)
    }

    fun setCountryFragment(root: List<Root?>?) {
        val args = Bundle()
        args.putParcelableArrayList(CountryData.ARG_PARAM1, root as ArrayList<out Parcelable?>?)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_left,
                R.anim.exit_to_left
            ) // enter    exit   pop enter pop exit
            .replace(R.id.recycle_fragment_view, CountryData::class.java, args)
            .commitAllowingStateLoss()
    }

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
            .replace(R.id.recycle_fragment_view, IndiaStateFragment::class.java, args)
            .commit()
    }

    private fun settingStart() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(false)
            .replace(R.id.recycle_fragment_view, Launching::class.java, null)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.search) makeToast("search")
        else if (itemId == R.id.call) makeToast("call")
        else if (itemId == R.id.change_theme) makeAlert()
        else if (itemId == R.id.safety) makeToast("Safety")
        else if (itemId == R.id.second || itemId == R.id.third) makeToast("progress")
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    fun makeToast(message: String?) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }

    private fun startIndianState() {
        val myMethod = NewsApi.indiaState?.create(Methods::class.java)
        if (myMethod != null) {
            myMethod.getContacts()?.enqueue(object : Callback<stateContacts?> {
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
            myMethod.getStates()?.enqueue(object : Callback<indiaStates?> {
                override fun onResponse(call: Call<indiaStates?>, response: Response<indiaStates?>) {
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

    private fun setCountries() {
        val method = NewsApi.apiInstance?.create(Methods::class.java)
        method?.getData()?.enqueue(object : Callback<List<Root?>?> {
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

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.search_bar_option) {
            if (country) startCountrySearch() else startStateSearch()
        }
        return true
    }

    private fun startStateSearch() {
        val intent = Intent(this@MainActivity, SearchHandle::class.java)
        intent.putExtra(values.COUNTRY_INTENT, "state")
        startActivity(intent)
    }

    private fun startCountrySearch() {
        val intent = Intent(this@MainActivity, SearchHandle::class.java)
        intent.putParcelableArrayListExtra(
            values.COUNTRY_VAL,
            rootList as ArrayList<out Parcelable?>?
        )
        intent.putExtra(values.COUNTRY_INTENT, "country")
        startActivity(intent)
    }

    override fun onRefresh() {
        if (country) setCountries() else startIndianState()
        Handler().postDelayed({ layout!!.isRefreshing = false }, 500)
    }

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
            .setPositiveButton("OK") { dialog1, which ->
                themeStatus =
                    if (light.isChecked) values.LIGHT else if (dark.isChecked) values.DARK else values.DEFAULT
            }
            .create()
        dialog.show()
    }

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