package com.example.coronatracker.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [World::class,Country::class,India::class],
    version = 2,
    exportSchema = false
)
abstract class TrackDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}