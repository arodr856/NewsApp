package com.example.rkjc.news_app_2.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    // https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=
    private static final String BASE_URL = "https://newsapi.org/v1/articles";
    private static final String SOURCE = "the-next-web";
    private static final String SORT_BY = "latest";
    private static final String API_KEY = "86cc0758798f4bc79fbac358ba5d52b7";



    public static URL buildURL(){

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("source", SOURCE)
                .appendQueryParameter("sortBy", SORT_BY)
                .appendQueryParameter("apiKey", API_KEY)
                .build();

        URL url = null;
        try{
            url = new URL(uri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    } /* buildURL */

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }else{
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    } /* getResponseFromHttpUrl */

} /* NetworkUtils */
