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

fun getIndiaObject(regional: regional, number: Regional) = India(
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



