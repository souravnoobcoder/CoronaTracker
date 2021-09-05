package com.example.coronatracker.Room;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {indiaStateModel.class}, version = 1, exportSchema = false)
public abstract class database extends RoomDatabase {
    public static database INSTANCE;

    public static database getDbINSTANCE(@Nullable Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), database.class, "contact.database")
                    .build();
        }
        return INSTANCE;
    }


    public abstract contactDao contactDao();
}
