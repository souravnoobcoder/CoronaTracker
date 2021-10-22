package com.example.coronatracker.dataClasses

data class IndiaData(
    val summary: Summary?,
    val unofficialSummary: List<UnofficialSummary?>?,
    val regional: List<Regiona?>?,
)
