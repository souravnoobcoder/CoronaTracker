package com.example.coronatracker.util

import com.example.coronatracker.dataClasses.*
import com.example.coronatracker.room.Country
import com.example.coronatracker.room.India
import com.example.coronatracker.room.WholeWorld

fun getWorldObject(world: World) = WholeWorld(
        world.population.toString(),
        world.cases.toString(),
        world.recovered.toString(),
        world.deaths.toString(),
        world.todayCases.toString(),
        world.active.toString(),
        world.todayDeaths.toString(),
        world.critical.toString(),
        world.casesPerOneMillion.toString(),
        world.deathsPerOneMillion.toString()
)

fun getIndiaObject(regional: Regiona, number: Regional) = India(
        regional.loc,
        regional.confirmedCasesForeign.toString(),
        regional.discharged.toString(),
        regional.deaths.toString(),
        regional.totalConfirmed.toString(),
        number.number,
        (regional.totalConfirmed?.minus(regional.discharged?.plus(regional.deaths!!)!!)).toString()
)

fun getCountryObject(country: Root,flag: String?) = Country(
        flag,
        country.country,
        country.population.toString(),
        country.cases.toString(),
        country.recovered.toString(),
        country.deaths.toString(),
        country.todayCases.toString(),
        country.active.toString(),
        country.todayDeaths.toString(),
        country.critical.toString(),
        country.casesPerOneMillion.toString(),
        country.deathsPerOneMillion.toString()
)

fun getCountryList(countries: List<Root>) =
        run {
                val listOfCountries: MutableList<Country> = ArrayList()
                for (i in countries.indices) {
                        val country = countries[i]
                        val countryObject = getCountryObject(country, country.countryInfo?.flag)
                        listOfCountries.add(countryObject)
                }
                listOfCountries.toList()
        }

fun getStateList(indiaStates: MutableList<Regiona?>?, stateContacts: MutableList<Regional>?) =
        run {
                val indices = indiaStates?.indices
                val stateList: MutableList<India> = ArrayList()
                for (i in indices!!) {
                        indiaStates[i]?.loc?.run {
                                var j =0;
                                while (j< stateContacts?.size!!){
                                        if (this == stateContacts[j].loc){
                                                stateList.add(getIndiaObject(
                                                        indiaStates[i]!!,
                                                        stateContacts[j]
                                                ))
                                        }
                                        stateContacts.removeAt(j)
                                        j++
                                }
                        }
                }
                stateList.toList()
        }
/**
 * @return index on which
 * @param state is present in
 * @param list
 */
fun getStateIndex(list: List<Regiona>, state:String) : Int{
        var i=0
        while (i<list.size){
                if (list[i].loc==state)
                        return i
                i++
        }
        return -1
}

/**
 * @return index on which
 * @param state is present in
 * @param list
 */
fun getStateContactIndex(list: List<Regional>,
                         state:String) : Int{
        var i=0
        while (i<list.size){
                if (list[i].loc==state)
                        return i
                i++
        }
        return -1
}

/**
 * @return index on which
 * @param country is present in
 * @param list
 */
fun getCountryIndex(list: List<Root>,country : String) : Int{
        var i=0
        while (i<list.size){
                if (list[i].country==country)
                        return i
                i++
        }
        return -1
}

