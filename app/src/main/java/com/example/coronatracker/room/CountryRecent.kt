package com.example.coronatracker.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countryRecent")
data class CountryRecent (
    @ColumnInfo(name="CountryName")val name: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
}

@Entity(tableName = "stateRecent")
data class StateRecent(
    @ColumnInfo(name = "StateName") val name: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
}