package com.example.rkjc.news_app_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.LoaderManager;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.CursorAdapter;

import com.example.rkjc.news_app_2.database.AppDatabase;
import com.example.rkjc.news_app_2.database.NewsItem;
import com.example.rkjc.news_app_2.database.NewsItemDao;
import com.example.rkjc.news_app_2.utils.JsonUtils;
import com.example.rkjc.news_app_2.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsItemsRepository {
    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> allNewsItems;
    private Application application;

    public NewsItemsRepository(Application application){
        AppDatabase db = AppDatabase.getsInstance(application.getApplicationContext());
        this.application = application;
        mNewsItemDao = db.newsItemDao();
        allNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems(){
        new getAllAsyncTask(mNewsItemDao, this).execute();
        return this.allNewsItems;
    }

    public List<NewsItem> syncDB(){
        try {
           return  new syncDatabase(mNewsItemDao, this, this.application).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setNewsItems(LiveData<List<NewsItem>> items){
        this.allNewsItems = items;
    }


    public static class getAllAsyncTask extends AsyncTask<Void, Void , LiveData<List<NewsItem>>>{

        private NewsItemDao newsItemDao;
        private NewsItemsRepository repo;
        private Application application;

        getAllAsyncTask(NewsItemDao newsItemDao, NewsItemsRepository repo){
            this.newsItemDao = newsItemDao;
            this.repo = repo;
        }

        @Override
        protected LiveData<List<NewsItem>> doInBackground(Void... avoid) {
            return this.newsItemDao.loadAllNewsItems();
        }

        @Override
        protected void onPostExecute(LiveData<List<NewsItem>> listLiveData) {
            super.onPostExecute(listLiveData);
            this.repo.setNewsItems(listLiveData);
        }
    }


    public static class syncDatabase extends AsyncTask<Void, Void, List<NewsItem>>{

        private NewsItemDao newsItemDao;
        private NewsItemsRepository repo;
        private Application application;

        syncDatabase(NewsItemDao newsItemDao, NewsItemsRepository repo, Application application){
            this.application = application;
            this.newsItemDao = newsItemDao;
            this.repo = repo;
        }

        @Override
        protected List<NewsItem> doInBackground(Void... voids) {

            String json = null;
            List<NewsItem> items = null;
            try {
                json = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildURL());
                items = JsonUtils.parseNews(json);
                newsItemDao.clearAll();
                newsItemDao.insert(items);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        }


    }


}
