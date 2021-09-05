package com.example.coronatracker.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface contactDao {
    @Insert
    void insert(indiaStateModel model);

    @Query("SELECT * FROM OFFLINECONTACTS")
    LiveData<List<indiaStateModel>> getOfflineData();

    @Query("DELETE FROM OFFLINECONTACTS")
    void deleteAll();

    @Query("SELECT * FROM OFFLINECONTACTS")
    List<indiaStateModel> getOfflineDataB();
}
