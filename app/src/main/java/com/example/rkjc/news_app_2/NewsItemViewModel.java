package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.rkjc.news_app_2.database.NewsItem;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsItemsRepository repo;
    private LiveData<List<NewsItem>> data;

    public NewsItemViewModel(@NonNull Application application) {
        super(application);
        this.repo = new NewsItemsRepository(application);
        this.data = repo.getAllNewsItems();
    }

    public LiveData<List<NewsItem>> getData() {
        return data;
    }

    public void sync(){
        Log.i("NewsItemViewModel", "in sync");
        this.repo.syncDB();
    }

}
