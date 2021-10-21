package com.example.coronatracker.dataClasses


import java.util.*

data class data(
    val summary: Summary?,
    val unofficialSummary: List<UnofficialSummary?>?,
    val regional: List<regional?>?,
)
data class indiaStates(
    val success: Boolean? ,
    val data: data?,
    val lastRefreshed: Date?,
    val lastOriginUpdate: Date?,
)

data class regional(
    val loc: String?,
    val confirmedCasesIndian: Int?,
    val confirmedCasesForeign: Int?,
    val discharged: Int?,
    val deaths: Int?,
    val totalConfirmed: Int?,
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