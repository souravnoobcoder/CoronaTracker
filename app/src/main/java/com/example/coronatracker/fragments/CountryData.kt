package com.example.coronatracker.fragments

import com.example.coronatracker.adapters.CountryAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coronatracker.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronatracker.dataClasses.Root
import java.util.ArrayList


class CountryData : Fragment() {
    private var DATA: ArrayList<Root>? = null
    private var adapter: CountryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            DATA = requireArguments().getParcelableArrayList(ARG_PARAM1)
        }
        adapter = CountryAdapter(DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.country_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recycle)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    companion object {
        const val ARG_PARAM1 = "param1"
    }
}