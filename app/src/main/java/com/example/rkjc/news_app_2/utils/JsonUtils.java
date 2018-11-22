package com.example.rkjc.news_app_2.utils;

import com.example.rkjc.news_app_2.database.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {


    public static ArrayList<NewsItem> parseNews(String jsonString){
        ArrayList<NewsItem> newsItemsList = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArticles = jsonObject.getJSONArray("articles");
            for(int i = 0; i < jsonArticles.length(); i++){
                JSONObject newsItem = jsonArticles.getJSONObject(i);
                NewsItem item = new NewsItem(newsItem.getString("author"), newsItem.getString("title"),
                        newsItem.getString("description"), newsItem.getString("url"), newsItem.getString("urlToImage"),
                        newsItem.getString("publishedAt"));
                newsItemsList.add(item);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return newsItemsList;
    }

}


