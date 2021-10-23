package com.example.coronatracker.activities


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronatracker.adapters.StateAdapter
import com.example.coronatracker.databinding.ActivitySearchHandleBinding
import com.example.coronatracker.features.TrackViewModel
import com.example.coronatracker.room.*
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*
@AndroidEntryPoint
class StateSearch : AppCompatActivity() {

    private lateinit var binding: ActivitySearchHandleBinding
    private val viewModel: TrackViewModel by viewModels()
    private var inputEditText: TextInputEditText? = null
    private var fullSearchingListState: List<India>? = null
    private var stateAdapter: StateAdapter? = null
    private var textWatcher: TextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing variables
        fullSearchingListState = ArrayList()
        stateAdapter=StateAdapter()
        setStateList()
        binding.run {
            inputEditText=searchBar
            searchedCountries.apply {
                layoutManager = LinearLayoutManager(this@StateSearch)
                adapter=stateAdapter
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
              stateAdapter!!.update(sortingOfState(s.toString().lowercase(Locale.getDefault())))
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
     * It sorts the list of state items with your searched string
     * And returns the sorted state list
     */
    fun sortingOfState(searched: String?): List<India> {
        val sortedList: MutableList<India> = ArrayList()
        for (i in fullSearchingListState!!.indices) {
            if (fullSearchingListState!![i].loc?.lowercase(Locale.getDefault())
                    ?.contains((searched)!!) == true
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
           fullSearchingListState= viewModel.getIndia()
        }
    }

}