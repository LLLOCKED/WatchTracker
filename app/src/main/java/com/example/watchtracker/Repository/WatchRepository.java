package com.example.watchtracker.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.watchtracker.Dao.WatchDao;
import com.example.watchtracker.Model.WatchModel;
import com.example.watchtracker.RoomDatabase.WatchListRoomDatabase;

import java.util.List;

public class WatchRepository {
    private WatchDao mWatchDao;
    private LiveData<List<WatchModel>> mWatchList;

    public WatchRepository(Application application) {
        WatchListRoomDatabase db = WatchListRoomDatabase.getDatabase(application);
        mWatchDao = db.watchDao();
        mWatchList = mWatchDao.getWatchList();
    }

    public LiveData<List<WatchModel>> getWatchList() {
        return mWatchList;
    }

    public LiveData<WatchModel> getWatchItemById(int id) {
        return mWatchDao.getWatchItemById(id);
    }

    public LiveData<List<WatchModel>> searchTitle(String title) {
        return mWatchDao.searchTitle(title);
    }

    public void deleteItem(WatchModel item) {
        WatchModel model = item;
        new deleteItemAsyncTask(mWatchDao).execute(item);
    }

    public void insert(WatchModel item) {
        new insertAsyncTask(mWatchDao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<WatchModel, Void, Void> {

        private WatchDao mAsyncWatchDao;

        insertAsyncTask(WatchDao dao) {
            mAsyncWatchDao = dao;
        }

        @Override
        protected Void doInBackground(final WatchModel... params) {
            mAsyncWatchDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteItemAsyncTask extends AsyncTask<WatchModel, Void, Void> {

        private WatchDao mAsyncWatchDao;

        deleteItemAsyncTask(WatchDao dao) {
            mAsyncWatchDao = dao;
        }

        @Override
        protected Void doInBackground(final WatchModel... params) {
            mAsyncWatchDao.deleteItem(params[0]);
            return null;
        }
    }

}
