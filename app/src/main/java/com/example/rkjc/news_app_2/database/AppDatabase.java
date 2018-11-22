package com.example.rkjc.news_app_2.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

//    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
//    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "newitem_database";

    private static AppDatabase INSTANCE;

    public abstract NewsItemDao newsItemDao();

    public static AppDatabase getsInstance(Context context) {
        if(INSTANCE == null) {
//            Log.d(LOG_TAG, "Creating a new database instance");
            synchronized (AppDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
            }
        }
        return INSTANCE;
    }

}
