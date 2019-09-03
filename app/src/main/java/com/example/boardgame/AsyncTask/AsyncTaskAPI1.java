package com.example.boardgame.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class AsyncTaskAPI1 extends AsyncTask <String, Void, InputStream>{

    protected InputStream doInBackground(String... recherche) {
        URL url = null;
        try {
            url = new URL("https://www.boardgamegeek.com/xmlapi/search?search=" + recherche + "&apikey=79c327454a97a8faa33d7b06f3d86e364da49e9c8766a0e10439ee47");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            return is;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(InputStream result) {
        Log.i("AsyncTask","");
    }



}

