package com.example.coronatracker.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class viewModel extends AndroidViewModel {
    private repo repository;
    private LiveData<List<indiaStateModel>> liveListData;
    public viewModel(@NonNull Application application) {
        super(application);
        this.repository=new repo(application);
        this.liveListData=this.repository.getOfflineData();
    }
    public void insert(indiaStateModel model){
        this.repository.insert(model);
    }
    public LiveData<List<indiaStateModel>> getOfflineData(){
        return this.liveListData;
    }
    public void deleteAll(){
        this.repository.deleteAll();
    }
}
