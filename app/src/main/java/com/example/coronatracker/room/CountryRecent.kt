package com.example.coronatracker.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countryRecent")
data class CountryRecent (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="CountryName")val name: String)

@Entity(tableName = "stateRecent")
data class StateRecent(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "StateName") val name: String
)