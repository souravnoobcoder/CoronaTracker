package com.example.coronatracker.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.R
import com.example.coronatracker.adapters.CountryAdapter
import com.example.coronatracker.adapters.StateAdapter
import com.example.coronatracker.databinding.ActivitySearchHandleBinding
import com.example.coronatracker.features.TrackViewModel
import com.example.coronatracker.room.Country
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class CountrySearch : AppCompatActivity() {

    private lateinit var binding: ActivitySearchHandleBinding
    private val viewModel: TrackViewModel by viewModels()
    private var inputEditText: TextInputEditText? = null
    private var fullSearchingList: List<Country>? = null
    private var countryAdapter: CountryAdapter? = null
    private var textWatcher: TextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing variables
        fullSearchingList = ArrayList()
        countryAdapter= CountryAdapter()
        setCountryList()
        binding.run {
            inputEditText=searchBar
            searchedCountries.run {
                layoutManager=LinearLayoutManager(this@CountrySearch)
                adapter=countryAdapter
            }
        }
        inputEditText?.isSelected = true
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
                countryAdapter!!.update(sorting(s.toString().lowercase(Locale.getDefault())))
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

    fun sorting(searched: String?): List<Country> {
        val searchingSortedList: MutableList<Country> = ArrayList()
        for (root: Country in fullSearchingList!!) {
            if (root.country?.lowercase(Locale.getDefault())?.contains((searched)!!) == true)
                searchingSortedList.add(root)
        }
        return searchingSortedList
    }

    private fun setCountryList() {
        CoroutineScope(Dispatchers.IO).launch {
            fullSearchingList = viewModel.getCountries()
        }
    }
}