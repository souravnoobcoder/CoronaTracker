package com.example.coronatracker.features

import androidx.room.withTransaction
import com.example.coronatracker.api.Methods
import com.example.coronatracker.room.Country
import com.example.coronatracker.room.India
import com.example.coronatracker.room.TrackDatabase
import com.example.coronatracker.util.getCountryObject
import com.example.coronatracker.util.getIndiaObject
import com.example.coronatracker.util.getWorldObject
import com.example.coronatracker.util.networkBoundResource
import javax.inject.Inject
import javax.inject.Named

class TrackRepository @Inject constructor(
    @Named(Methods.INDIA_API) private val indiaApi: Methods,
    @Named(Methods.WORLD_API) private val worldApi: Methods,
    @Named(Methods.COUNTRY_API) private val countryApi: Methods,
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
            val listOfCountries: MutableList<Country> = ArrayList()
            for (i in countries.indices) {
                val country = countries[i]
                val countryObject = getCountryObject(country, country.countryInfo?.flag)
                listOfCountries.add(countryObject)
            }
            database.withTransaction {
                dao.deleteCountryAll()
                dao.insertCountries(listOfCountries)
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