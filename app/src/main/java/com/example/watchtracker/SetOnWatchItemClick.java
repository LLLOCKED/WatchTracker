package com.example.watchtracker;

import com.example.watchtracker.Model.WatchModel;

public interface SetOnWatchItemClick {
    void onWatchItemClicked(int id);

    void onWatchItemDelete(WatchModel item);

}
