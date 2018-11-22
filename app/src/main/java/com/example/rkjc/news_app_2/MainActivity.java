package com.example.rkjc.news_app_2;


import android.annotation.SuppressLint;
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
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = "MainActivity";


    private static final String NEWS_RESULTS = "newsResults";
    private static final int LOADER_ID = 1;
    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<NewsItem> newsItems = new ArrayList<>();
    private String newsStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recyclerview);
        recyclerViewAdapter = new NewsRecyclerViewAdapter(newsItems, this);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState != null && savedInstanceState.getString(NEWS_RESULTS) != null){
            Log.i(TAG, "In savedInstanceState not null check. . .");
            String newsResults = savedInstanceState.getString(NEWS_RESULTS);
            populateRecyclerView(newsResults);
        }
//        else{
//            Log.i(TAG, "Right before savedInstanceState not null check. . .");
//            LoaderManager loaderManager = getSupportLoaderManager();
//            Loader<String> newsHolder = loaderManager.getLoader(LOADER_ID);
//            if(newsHolder == null){
//                Log.i(TAG, "News holder was null . . .");
//                loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
//            }else{
//                Log.i(TAG, "News holder was not null . . .");
//                loaderManager.restartLoader(LOADER_ID, null, this).forceLoad();
//            }
//        }

    }

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
                LoaderManager loaderManager = getSupportLoaderManager();
                Loader<String> newsHolder = loaderManager.getLoader(LOADER_ID);
                if(newsHolder == null){
                    Log.i(TAG, "News holder was null . . .");
                    loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
                }else{
                    Log.i(TAG, "News holder was not null . . .");
                    loaderManager.restartLoader(LOADER_ID, null, this).forceLoad();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateRecyclerView(String jsonString){
        Log.i(TAG, "starting populateRecyclerView. . .");
        newsItems = JsonUtils.parseNews(jsonString);
        recyclerViewAdapter.getNewsItems().addAll(newsItems);
        recyclerViewAdapter.notifyDataSetChanged();
        Log.i(TAG, "finishing populateRecyclerView. . .");
    }


    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(this) {


            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }

            @Override
            public String loadInBackground() {
                Log.i(TAG, "starting loadInBackground. . .");
                NewsQueryTask nqt = new NewsQueryTask();
                nqt.execute();
                String jsonString;
                try {
                    jsonString = nqt.get();
//                    newsStrings = jsonString;
                    Log.i(TAG, jsonString);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    return null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
                Log.i(TAG, "finishing in loadInBackground. . .");
                return jsonString;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.i(TAG, "starting onLoadFinished. . .");
        newsStrings = data;
        populateRecyclerView(data);
        Log.i(TAG, "finishing onLoadFinished. . .");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

}
