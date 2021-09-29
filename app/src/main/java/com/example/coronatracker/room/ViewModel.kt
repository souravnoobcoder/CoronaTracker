package com.example.coronatracker.room


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repo = Repo(application)
    val offlineData: LiveData<List<indiaStateModel?>?>? = repository.offlineData

    suspend fun insert(model: indiaStateModel?) = repository.insert(model)

    suspend fun getOfflineDataB() : List<indiaStateModel?> = repository.getOfflineDataB()

    suspend fun deleteAll() = repository.deleteAll()

    suspend fun insertState(state: StateRecent) = repository.insertState(state)

    suspend fun insertCountry(country: CountryRecent) = repository.insertCountry(country)

    fun getStateRecent() : LiveData<List<StateRecent>> =repository.getRecentState()

    fun getCountryRecent(): LiveData<List<CountryRecent>> =repository.getRecentCountry()
}