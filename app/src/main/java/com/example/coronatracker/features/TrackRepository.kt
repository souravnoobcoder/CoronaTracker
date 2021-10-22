package com.example.coronatracker.features

import androidx.room.withTransaction
import com.example.coronatracker.api.Methods
import com.example.coronatracker.dataClasses.Constants
import com.example.coronatracker.room.Country
import com.example.coronatracker.room.India
import com.example.coronatracker.room.TrackDatabase
import com.example.coronatracker.util.*
import javax.inject.Inject
import javax.inject.Named

class TrackRepository @Inject constructor(
    @Named(Constants.INDIA_API) private val indiaApi: Methods,
    @Named(Constants.WORLD_API) private val worldApi: Methods,
    @Named(Constants.COUNTRY_API) private val countryApi: Methods,
    private val database: TrackDatabase
) {
    private val dao = database.trackDao()

    fun getWorld() = networkBoundResource(
        query = {
            dao.getWorld()
        },
        fetch = {
            worldApi.getWorld()
        },
        saveFetchResult = { world ->
            database.withTransaction {
                dao.deleteWorldAll()
                dao.insertWorld(getWorldObject(world))
            }
        }
    )

    fun getCountries() = networkBoundResource(
        query = {
            dao.getCountries()
        },
        fetch = {
            countryApi.getCountries()
        },
        saveFetchResult = { countries ->
            database.withTransaction {
                dao.deleteCountryAll()
                dao.insertCountries(getCountryList(countries))
            }
        }
    )

    fun getIndia() = networkBoundResource(
        query = {
            dao.getIndia()
        },
        fetch = {
            val indiaStates = indiaApi.getIndiaStates()
            val indiaStateContacts = indiaApi.getIndiaStateContacts()
            Pair(indiaStates, indiaStateContacts)
        },
        saveFetchResult = { indiaPair ->
            val indiaList = indiaPair.first.data?.regional
            val indiaContactList = indiaPair.second.data?.contacts?.regional
            getStateList(indiaList?.toMutableList(),indiaContactList?.toMutableList())
            val indicesOfIndiaList = indiaList?.indices!!
            val indiaRoomList: MutableList<India> = ArrayList()
            for (i in indicesOfIndiaList) {
                val indiaObject= getIndiaObject(
                    indiaList[i]!!,
                    indiaContactList?.get(i)!!
                )
                indiaRoomList.add(indiaObject)
            }
            database.withTransaction {
                dao.deleteIndiaAll()
                dao.insertIndia(indiaRoomList)
            }

        }
    )
    suspend fun getCountriesD() = dao.getCountriesD()

    suspend fun getIndiaD() = dao.getIndiaD()

}