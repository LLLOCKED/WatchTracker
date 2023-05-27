package com.example.watchtracker.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.watchtracker.Dao.WatchDao;
import com.example.watchtracker.Model.WatchModel;
import com.example.watchtracker.Repository.WatchRepository;
import com.example.watchtracker.RoomDatabase.WatchListRoomDatabase;

import java.util.List;


public class WatchViewModel extends AndroidViewModel {
    private WatchRepository mRepository;

    private LiveData<List<WatchModel>> mWatchList;

    public WatchViewModel(Application application) {
        super(application);
        mRepository = new WatchRepository(application);
        mWatchList = mRepository.getWatchList();
    }

    public void deleteItem(WatchModel item) {
        mRepository.deleteItem(item);
    }

    public LiveData<List<WatchModel>> getWatchList() {
        return mWatchList;
    }

    public LiveData<WatchModel> getWatchItemById(int id) {
        return mRepository.getWatchItemById(id);
    }

    public LiveData<List<WatchModel>> searchTitle(String title) {
        mWatchList = mRepository.searchTitle(title);
        return mRepository.searchTitle(title);
    }

    public void addItem(WatchModel item) {
        mRepository.insert(item);
    }
}
