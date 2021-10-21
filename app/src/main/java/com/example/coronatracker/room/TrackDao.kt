package com.example.coronatracker.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.coronatracker.room.WholeWorld
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert
    suspend fun insertIndia(indiaList: List<India>)

    @Insert(onConflict = REPLACE)
    suspend fun insertWorld(world: WholeWorld)

    @Insert(onConflict = REPLACE)
    suspend fun insertCountries(countries: List<Country>)


    @Query("DELETE FROM world")
    suspend fun deleteWorldAll()

    @Query("DELETE FROM country")
    suspend fun deleteCountryAll()

    @Query("DELETE FROM india")
    suspend fun deleteIndiaAll()


    @Query("SELECT * FROM india")
    fun getIndia(): Flow<List<India>>

    @Query("SELECT * FROM world")
    fun getWorld(): Flow<WholeWorld>

    @Query("SELECT * FROM country")
    fun getCountries(): Flow<List<Country>>


    @Query("SELECT * FROM india")
    suspend fun getIndiaD(): List<India>

    @Query("SELECT * FROM country")
    suspend fun getCountriesD(): List<Country>



}