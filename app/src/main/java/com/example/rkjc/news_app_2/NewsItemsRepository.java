package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.rkjc.news_app_2.database.AppDatabase;
import com.example.rkjc.news_app_2.database.NewsItem;
import com.example.rkjc.news_app_2.database.NewsItemDao;

import java.util.List;

public class NewsItemsRepository {
    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> allNewsItems;

    public NewsItemsRepository(Application application){
        AppDatabase db = AppDatabase.getsInstance(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        allNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems(){
        return this.allNewsItems;
    }



}
