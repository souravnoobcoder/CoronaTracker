package com.example.coronatracker.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private val repository: TrackRepository
) : ViewModel() {
    val india = repository.getIndia().asLiveData()

    val world = repository.getWorld().asLiveData()

    val countries = repository.getCountries().asLiveData()

    suspend fun getCountries() = repository.getCountriesD()

    suspend fun getIndia() = repository.getIndiaD()
}