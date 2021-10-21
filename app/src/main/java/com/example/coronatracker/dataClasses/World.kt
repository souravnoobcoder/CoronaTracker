package com.example.coronatracker.dataClasses

data class World(
    val updated: Long?,
    val cases: Int?,
    val todayCases: Int?,
    val deaths: Int?,
    val todayDeaths: Int?,
    val recovered: Int?,
    val todayRecovered: Int?,
    val active: Int?,
    val critical: Int?,
    val casesPerOneMillion: Int?,
    val deathsPerOneMillion: Double?,
    val tests: Long?,
    val testsPerOneMillion: Double?,
    val population: Long?,
    val oneCasePerPeople: Int?,
    val oneDeathPerPeople: Int?,
    val oneTestPerPeople: Int?,
    val activePerOneMillion: Double?,
    val recoveredPerOneMillion: Double?,
    val criticalPerOneMillion: Double?,
    val affectedCountries: Int?
)