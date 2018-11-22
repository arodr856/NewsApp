package com.example.rkjc.news_app_2;

import android.os.AsyncTask;

import com.example.rkjc.news_app_2.utils.NetworkUtils;

import java.io.IOException;

public class NewsQueryTask extends AsyncTask<Void, Void, String> {


    @Override
    protected String doInBackground(Void... voids) {
        try {
            String json = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildURL());
//            Log.i("tag",json);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}