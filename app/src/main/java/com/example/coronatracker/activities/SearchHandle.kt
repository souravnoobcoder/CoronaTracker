package com.example.coronatracker.activities


import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.example.coronatracker.room.indiaStateModel
import com.example.coronatracker.Adapters.countryAdapter
import com.example.coronatracker.Adapters.stateAdapter
import android.text.TextWatcher
import android.os.Bundle
import com.example.coronatracker.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronatracker.dataClasses.values
import android.text.Editable
import androidx.lifecycle.ViewModelProviders
import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.room.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.ArrayList

class SearchHandle() : AppCompatActivity() {
    var inputEditText: TextInputEditText? = null
    var know: String? = null
    val model: viewModel= ViewModelProviders.of(this).get(viewModel::class.java)
    private var fullSearchingList: List<Root>? = null
    private var fullSearchingListState: List<indiaStateModel?>? = null
    var adapter: countryAdapter? = null
    var adapterS: stateAdapter? = null
    var textWatcher: TextWatcher? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_handle)
        fullSearchingListState = ArrayList()
        fullSearchingList = ArrayList()
        inputEditText = findViewById(R.id.search_bar)
        val recyclerView = findViewById<RecyclerView>(R.id.searched_countries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        know = intent.getStringExtra(values.COUNTRY_INTENT)
        if ((know == "country")) {
            inputEditText?.setHint("Enter Country")
            adapter = countryAdapter(null)
            fullSearchingList = intent.getParcelableArrayListExtra(values.COUNTRY_VAL)
            recyclerView.adapter = adapter
        } else {
            inputEditText?.setHint("Enter state")
            adapterS = stateAdapter(null)
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
                        s.toString().toLowerCase()
                    )
                ) else adapterS!!.update(sortingOfState(s.toString().toLowerCase()))
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
            if (root.country.toLowerCase().contains((searched)!!)) searchingSortedList.add(root)
        }
        return searchingSortedList
    }

    fun sortingOfState(searched: String?): List<indiaStateModel?> {
        val sortedList: MutableList<indiaStateModel?> = ArrayList()
        for (i in fullSearchingListState!!.indices) {
            if (fullSearchingListState!![i]!!.loc.toLowerCase()
                    .contains((searched)!!)
            ) sortedList.add(
                fullSearchingListState!![i]
            )
        }
        return sortedList
    }

    private fun setStateList() {
        CoroutineScope(IO).launch {
           fullSearchingListState= model.getOfflineDataB()
        }
    }
}