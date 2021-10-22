package com.example.coronatracker.dataClasses

import java.util.*

data class Contacts (
    val primary: Primary?,
    val regional: List<Regional>?
)
data class Primary(
    val number: String? ,
    val numberTollfree: String? ,
    val email: String? ,
    val twitter: String? ,
    val facebook: String? ,
    val media: List<String?>?
)
data class Data(
    val contacts: Contacts?
)
data class StateContacts(
    val success : Boolean?,
    val data: Data? ,
    val lastRefreshed: Date? ,
    val lastOriginUpdate: Date?
)