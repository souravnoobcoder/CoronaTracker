package com.example.coronatracker.activities


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.R
import com.example.coronatracker.adapters.CountryAdapter
import com.example.coronatracker.adapters.CountryHistoryAdapter
import com.example.coronatracker.adapters.StateAdapter
import com.example.coronatracker.adapters.StateHistoryAdapter
import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.dataClasses.values
import com.example.coronatracker.room.CountryRecent
import com.example.coronatracker.room.ViewModel
import com.example.coronatracker.room.indiaStateModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

class SearchHandle : AppCompatActivity() {
    private var inputEditText: TextInputEditText? = null
    var know: String? = null
    private var model: ViewModel?=null
    private var fullSearchingList: List<Root>? = null
    private var fullSearchingListState: List<indiaStateModel?>? = null
    var adapter: CountryAdapter? = null
    var adapterS: StateAdapter? = null
    private var textWatcher: TextWatcher? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_handle)

        // Initializing variables
        fullSearchingListState = ArrayList()
        fullSearchingList = ArrayList()
        inputEditText = findViewById(R.id.search_bar)
        model= ViewModelProviders.of(this@SearchHandle).get(ViewModel::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.searched_countries)
        recyclerView.layoutManager = LinearLayoutManager(this)

        /**
         * Checking which adapter should be initialized by checking extras in intent
         */
        know = intent.getStringExtra(values.COUNTRY_INTENT)
        if ((know == "country")) {
            inputEditText?.hint = "Enter Country"
            adapter = CountryAdapter(null)
            fullSearchingList = intent.getParcelableArrayListExtra(values.COUNTRY_VAL)
            recyclerView.adapter = adapter
        } else {
            inputEditText?.hint = "Enter state"
            adapterS = StateAdapter(null)
            recyclerView.adapter = adapterS
            setStateList()
        }
        inputEditText?.isSelected = true

        /**
         * Setting data in adapter by checking extras in intent
         */
        val recentRecycle =findViewById<RecyclerView>(R.id.history_recycle)
        var historyAdapter: StateHistoryAdapter?
        var historyCountryAdapter: CountryHistoryAdapter? =null
        recentRecycle.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        if (know == "country") {
            model?.getCountryRecent()?.observe(this,
                { t -> historyCountryAdapter=CountryHistoryAdapter(t!!)
                    recentRecycle.adapter=historyCountryAdapter
                })
        }
        else {
            model?.getStateRecent()?.observe(this,
                { t ->  historyAdapter=StateHistoryAdapter(t!!)
                    recentRecycle.adapter=historyAdapter})
        }
    }

    /**
     * When onStart() is called we start watching the text changes in Edittext
     * and add textChangeListener
     */
    override fun onStart() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if ((know == "country")) adapter!!.update(
                    sorting(
                        s.toString().lowercase(Locale.getDefault())
                    )
                ) else adapterS!!.update(sortingOfState(s.toString().lowercase(Locale.getDefault()))
                        as List<indiaStateModel>)
            }
        }
        inputEditText!!.addTextChangedListener(textWatcher)
        super.onStart()
    }

    /**
     * When onStop() is called means user is no longer interacting with our application so we
     * removes our textChangeListener
     */
    override fun onStop() {
        inputEditText!!.removeTextChangedListener(textWatcher)
        super.onStop()
    }

    /**
     * It sorts the list of country items with your searched string
     * And returns the sorted country list
     */
    fun sorting(searched: String?): List<Root> {
        val searchingSortedList: MutableList<Root> = ArrayList()
        for (root: Root in fullSearchingList!!) {
            if (root.country.lowercase(Locale.getDefault()).contains((searched)!!))
                searchingSortedList.add(root)
        }
        return searchingSortedList
    }

    /**
     * It sorts the list of state items with your searched string
     * And returns the sorted state list
     */
    fun sortingOfState(searched: String?): List<indiaStateModel?> {
        val sortedList: MutableList<indiaStateModel?> = ArrayList()
        for (i in fullSearchingListState!!.indices) {
            if (fullSearchingListState!![i]!!.loc.lowercase(Locale.getDefault())
                    .contains((searched)!!)
            ) sortedList.add(
                fullSearchingListState!![i]
            )
        }
        return sortedList
    }

    /**
     * get list of data by launching a coroutine
     */
    private fun setStateList() {
        CoroutineScope(IO).launch {
           fullSearchingListState= model?.getOfflineDataB()
        }
    }

}