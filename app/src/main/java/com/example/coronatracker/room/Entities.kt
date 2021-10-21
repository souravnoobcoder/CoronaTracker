package com.example.coronatracker.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "World")
data class WholeWorld(
    val totalPopulation: String?,
    val confirmed: String?,
    val recovered: String?,
    val deaths: String?,
    val casesToday: String?,
    val activeCases: String?,
    val deathsToday: String?,
    val criticalCases: String?,
    val casesPerMillion: String?,
    val deathsPerMillion: String?
) {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
}
@Entity(tableName = "Country")
data class Country(
    val flagUrl: String?,
    val country: String?,
    val totalPopulation: String?,
    val confirmed: String?,
    val recovered: String?,
    val deaths: String?,
    val casesToday: String?,
    val activeCases: String?,
    val deathsToday: String?,
    val criticalCases: String?,
    val casesPerMillion: String?,
    val deathsPerMillion: String?
) {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
}

@Entity(tableName = "India")
data class India(
    val loc: String?,
    val confirmedCasesForeign: String?,
    val recovered: String?,
    val deaths: String?,
    val totalConfirmed: String?,
    val number: String?,
    val activeCases: String?
) {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
}