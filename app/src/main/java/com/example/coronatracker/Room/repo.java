package com.example.coronatracker.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class repo {
    private final contactDao dao;
    private final LiveData<List<indiaStateModel>> listLiveData;

    public repo(Application application) {
        database base = database.getDbINSTANCE(application);
        this.dao = base.contactDao();
        this.listLiveData = dao.getOfflineData();
    }

    public LiveData<List<indiaStateModel>> getOfflineData() {
        return listLiveData;
    }

    public void insert(indiaStateModel model) {
        new insertAsyncTask(this.dao).execute(model);
    }

    private static class insertAsyncTask extends AsyncTask<indiaStateModel, Void, Void> {
        contactDao AsyncDao;

        public insertAsyncTask(contactDao asyncDao) {
            AsyncDao = asyncDao;
        }

        @Override
        protected Void doInBackground(indiaStateModel... indiaStateModels) {
            AsyncDao.insert(indiaStateModels[0]);
            return null;
        }
    }

    public void deleteAll() {
        new deleteAsync(this.dao).execute();
    }

    private class deleteAsync extends AsyncTask<Void, Void, Void> {
        private final contactDao AsyncDao;

        public deleteAsync(contactDao asyncDao) {
            AsyncDao = asyncDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AsyncDao.deleteAll();
            return null;
        }
    }
}
