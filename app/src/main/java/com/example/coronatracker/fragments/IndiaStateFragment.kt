package com.example.coronatracker.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.R
import com.example.coronatracker.activities.MainActivity
import com.example.coronatracker.adapters.StateAdapter
import com.example.coronatracker.dataClasses.indiaModel.Regional
import com.example.coronatracker.room.indiaStateModel
import com.example.coronatracker.room.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class IndiaStateFragment : Fragment() {
    private var myModel: ViewModel? = null
    private var states: ArrayList<Regional>? = null
    private var contacts: ArrayList<com.example.coronatracker.dataClasses.indiaContactModel.Regional>? =
        null
    private var adapter: StateAdapter? = null
    var data: List<indiaStateModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myModel = ViewModelProviders.of(requireActivity()).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_india_state, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        states = ArrayList()
        contacts = ArrayList()
        data = ArrayList()
        if (arguments != null) {
            states = requireArguments().getParcelableArrayList(getString(R.string.state_key))
            contacts = requireArguments().getParcelableArrayList(getString(R.string.state_cont_key))
        } else {
            println(MainActivity.TAGO + "arguments are zero")
            Log.v(MainActivity.TAGO, "arguments are zero")
        }
        Log.v(MainActivity.TAGO," states ${states?.size}")
        Log.v(MainActivity.TAGO," contacts ${contacts?.size}")
        if (states == null || states!!.isEmpty() || contacts == null || contacts!!.isEmpty()) {
            println(MainActivity.TAGO + "states or contacts is null")
            Log.v(MainActivity.TAGO, "states or contacts is null")
        }
        val recyclerView: RecyclerView = requireView().findViewById(R.id.stateRecycle)
        recyclerView.layoutManager = LinearLayoutManager(context)
        CoroutineScope(Default).launch {
            data = createData()
            CoroutineScope(Main).launch {
                if (data!!.isNotEmpty()) {
                    adapter = StateAdapter(data)
                    recyclerView.adapter = adapter
                    setRoom()
                } else {
                    myModel!!.offlineData!!.observeForever { indiaStateModels: List<indiaStateModel?>? ->
                        adapter = StateAdapter(indiaStateModels as List<indiaStateModel>?)
                        recyclerView.adapter = adapter
                    }
                    println(MainActivity.TAGO + "data size is zero")
                    Log.v(MainActivity.TAGO, "data size is zero")
                }
            }
        }

    }

    private fun createData(): List<indiaStateModel> {
        val model: MutableList<indiaStateModel> = ArrayList()
        if (states != null && contacts != null) {
            if (states!!.isEmpty() || contacts!!.isEmpty()) {
                println(MainActivity.TAGO + "value inside")
                Log.v(MainActivity.TAGO, "value inside")
            }
            var i = 0
            while (i < states!!.size) {
                val state = states!![i]
                var j = 0
                while (j < contacts!!.size) {
                    val contact = contacts!![j]
                    val stateNameI = state.loc
                    val stateNameJ = contact.loc
                    if (stateNameI == stateNameJ) {
                       // contacts!!.remove(contact)
                        val confirmedCasesForeign = state.confirmedCasesForeign
                        val discharged = state.discharged
                        val deaths = state.deaths
                        val totalConfirmed = state.totalConfirmed
                        val number = contact.number
                        val active = totalConfirmed - (deaths + discharged)
                        model.add(
                            indiaStateModel(
                                stateNameI, confirmedCasesForeign, discharged, deaths,
                                totalConfirmed, number, active
                            )
                        )
                    }
                    j++
                }
                i++
            }
        }
        if (model.isEmpty()) println("empty") else println("not empty")
        return model
    }

    private fun setRoom() {
        CoroutineScope(IO).launch {
            myModel!!.deleteAll()
            for (i in data!!.indices) {
                myModel!!.insert(data!![i])
            }
        }
    }
}