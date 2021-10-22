package com.example.coronatracker.dataClasses

data class Regiona(
    val loc: String?,
    val confirmedCasesIndian: Int?,
    val confirmedCasesForeign: Int?,
    val discharged: Int?,
    val deaths: Int?,
    val totalConfirmed: Int?,
)
