package com.example.rkjc.news_app_2;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.rkjc.news_app_2.database.NewsItem;
import com.example.rkjc.news_app_2.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";


    private static final String NEWS_RESULTS = "newsResults";
    private static final int LOADER_ID = 1;
    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter recyclerViewAdapter;
    private List<NewsItem> newsItems = new ArrayList<>();
    private String newsStrings;
    private NewsItemViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recyclerview);

        this.newsViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);

        recyclerViewAdapter = new NewsRecyclerViewAdapter(newsItems, this);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsViewModel.getData().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                Log.i("MainActivity", "In observer");
                recyclerViewAdapter.setNewsItems(newsItems);
            }
        });

    } /* onCreate */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "starting onSaveInstanceState. . .");
//        Log.i(TAG,newsStrings);
        outState.putString(NEWS_RESULTS, newsStrings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.get_item:
                this.newsViewModel.sync();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
