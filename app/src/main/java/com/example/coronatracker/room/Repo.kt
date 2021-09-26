package com.example.coronatracker.room

import android.app.Application
import androidx.lifecycle.LiveData

class Repo(application: Application?) {
    private val dao: ContactDao
    val offlineData: LiveData<List<indiaStateModel?>?>?

    suspend fun insert(model: indiaStateModel?) = dao.insert(model)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun getOfflineDataB() : List<indiaStateModel> = dao.getOfflineDataB()

    suspend fun insertState(state: StateRecent) = dao.insertState(state)

    suspend fun insertCountry(country: CountryRecent) = dao.insertCountry(country)

    fun getRecentState() : LiveData<List<StateRecent>> =dao.getRecentState()

    fun getRecentCountry(): LiveData<List<CountryRecent>> =dao.getRecentCountry()

    init {
        val base = database.getDbINSTANCE(application)
        dao = base.contactDao()
        offlineData = dao.getOfflineData()
    }
}