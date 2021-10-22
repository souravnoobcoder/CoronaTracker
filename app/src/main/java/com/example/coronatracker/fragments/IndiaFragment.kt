package com.example.coronatracker.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coronatracker.R
import com.example.coronatracker.adapters.StateAdapter
import com.example.coronatracker.databinding.FragmentIndiaStateBinding
import com.example.coronatracker.features.TrackViewModel
import com.example.coronatracker.util.Resource

class IndiaFragment : Fragment() {

    private val viewModel: TrackViewModel by viewModels()
    private lateinit var binding: FragmentIndiaStateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndiaStateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stateAdapter = StateAdapter()
        binding.run {
            stateRecycle.run {
                layoutManager = LinearLayoutManager(context)
                adapter = stateAdapter
            }
            viewModel.india.observe(viewLifecycleOwner){
                it?.data?.let { i-> stateAdapter.update(i) }
                progress.isVisible = (it is Resource.Loading && it.data.isNullOrEmpty())
            }
        }

    }
}