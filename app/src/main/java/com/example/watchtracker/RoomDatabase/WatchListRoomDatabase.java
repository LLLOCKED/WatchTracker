package com.example.watchtracker.RoomDatabase;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.watchtracker.Dao.WatchDao;
import com.example.watchtracker.Model.WatchModel;

@Database(entities = {WatchModel.class}, version = 1, exportSchema = false)
public abstract class WatchListRoomDatabase extends RoomDatabase {
    public abstract WatchDao watchDao();

    private static WatchListRoomDatabase INSTANCE;

    public static WatchListRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WatchListRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    WatchListRoomDatabase.class, "watch_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WatchDao mDao;

        PopulateDbAsync(WatchListRoomDatabase db) {
            mDao = db.watchDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            //mDao.deleteAll();

            return null;
        }
    }
}
