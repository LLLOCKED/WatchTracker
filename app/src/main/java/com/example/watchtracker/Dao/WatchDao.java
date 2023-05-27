package com.example.watchtracker.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.watchtracker.Model.WatchModel;

import java.util.List;

@Dao
public interface WatchDao {

    @Insert
    void insert(WatchModel watchItem);

    @Query("DELETE FROM watch_list")
    void deleteAll();

    @Delete
    void deleteItem(WatchModel watchItem);

    @Query("SELECT * FROM watch_list ORDER BY id ASC")
    LiveData<List<WatchModel>> getWatchList();

    @Query("SELECT * FROM watch_list WHERE id = :id")
    LiveData<WatchModel> getWatchItemById(int id);

    @Query("SELECT * FROM watch_list WHERE title LIKE :title")
    LiveData<List<WatchModel>> searchTitle(String title);
}
