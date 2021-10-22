package com.example.coronatracker.dataClasses


import java.util.*

data class indiaStates(
    val success: Boolean?,
    val data: IndiaData?,
    val lastRefreshed: Date?,
    val lastOriginUpdate: Date?,
)


data class Summary(
    val total: Int?,
    val confirmedCasesIndian: Int?,
    val confirmedCasesForeign: Int?,
    val discharged: Int?,
    val deaths: Int?,
    val confirmedButLocationUnidentified: Int?
)
data class UnofficialSummary(
    val total: Int?,
    val confirmedCasesIndian: Int?,
    val confirmedCasesForeign: Int?,
    val discharged: Int?,
    val deaths: Int?,
    val confirmedButLocationUnidentified: Int?
)