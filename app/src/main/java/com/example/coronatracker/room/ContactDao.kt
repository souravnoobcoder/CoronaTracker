package com.example.coronatracker.room

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(model: indiaStateModel?)

    @Query("SELECT * FROM offlineContacts")
    fun getOfflineData(): LiveData<List<indiaStateModel?>?>?

    @Query("DELETE FROM offlineContacts")
    suspend fun deleteAll()

    @Query("SELECT * FROM offlineContacts")
    suspend fun getOfflineDataB() : List<indiaStateModel>

    @Insert
    suspend fun insertCountry(country: CountryRecent)

    @Query("SELECT * FROM countryRecent")
    fun getRecentCountry() :LiveData<List<CountryRecent>>

    @Insert
    suspend fun insertState(country: StateRecent)

    @Query("SELECT * FROM stateRecent")
    fun getRecentState() :LiveData<List<StateRecent>>
}