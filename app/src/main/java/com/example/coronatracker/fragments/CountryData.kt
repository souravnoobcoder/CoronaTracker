package com.example.coronatracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronatracker.adapters.CountryAdapter
import com.example.coronatracker.databinding.CountryDataBinding
import com.example.coronatracker.features.TrackViewModel
import com.example.coronatracker.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryData : Fragment() {

    private val viewModel: TrackViewModel by viewModels()
    private lateinit var binding: CountryDataBinding
    private var countryAdapter: CountryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        countryAdapter = CountryAdapter()
        binding = CountryDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            recycle.run {
                layoutManager = LinearLayoutManager(context)
                adapter = countryAdapter
            }
            viewModel.countries.observe(viewLifecycleOwner){
                it.data?.let { i -> countryAdapter?.update(i) }
                progress.isVisible = (it is Resource.Loading && it.data.isNullOrEmpty())
            }
        }
    }
    companion object {
        const val ARG_PARAM1 = "param1"
    }
}