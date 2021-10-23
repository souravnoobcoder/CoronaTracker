package com.example.coronatracker.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.coronatracker.room.India
import com.example.coronatracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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