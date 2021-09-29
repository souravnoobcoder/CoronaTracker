package com.example.coronatracker.activities


import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.example.coronatracker.room.indiaStateModel
import com.example.coronatracker.adapters.CountryAdapter
import com.example.coronatracker.adapters.StateAdapter
import android.text.TextWatcher
import android.os.Bundle
import com.example.coronatracker.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronatracker.dataClasses.values
import android.text.Editable
import androidx.lifecycle.ViewModelProviders
import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.room.ViewModel
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
        fullSearchingListState = ArrayList()
        fullSearchingList = ArrayList()
        inputEditText = findViewById(R.id.search_bar)
        model= ViewModelProviders.of(this@SearchHandle).get(ViewModel::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.searched_countries)
        recyclerView.layoutManager = LinearLayoutManager(this)
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
    }

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

    override fun onStop() {
        inputEditText!!.removeTextChangedListener(textWatcher)
        super.onStop()
    }

    fun sorting(searched: String?): List<Root> {
        val searchingSortedList: MutableList<Root> = ArrayList()
        for (root: Root in fullSearchingList!!) {
            if (root.country.lowercase(Locale.getDefault()).contains((searched)!!))
                searchingSortedList.add(root)
        }
        return searchingSortedList
    }

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

    private fun setStateList() {
        CoroutineScope(IO).launch {
           fullSearchingListState= model?.getOfflineDataB()
        }
    }
}