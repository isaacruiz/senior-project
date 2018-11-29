package com.project.senior.myapplication;
/*
import android.util.Log;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {

    private final static String baseurl = "https://trailapi-trailapi.p.mashape.com/";
    private final static String endurl = "&mashape-key=pKccbSem6FmshfKcq5kMbN60uwHgp1dMFkPjsnEvm8ubvA7hjD";
    private String midurl;
    String city = "Mission";
    String state = "Texas";
    String country = "United States";
    int radius = 200;

    //Make constructor more general//
    public Request(){
        midurl = "?q[city_cont]=" + this.city + "&q[country_cont]=" + this.country + "&q[state_cont]=" + this.state + "&radius=" + this.radius + "";
    }
    public void TestRequest() {
        /*
        try {
            HttpResponse<String> response = Unirest.get("https://trailapi-trailapi.p.mashape.com/?q-city_cont=Mission&q-country_cont=United+States&q-state_cont=Texas")
                    .header("X-Mashape-Key", "7KsUvly5VOmsh9VDoPTll3vugdPVp1CPDGRjsnfyh3RQ3daUvm")
                    .header("Accept", "text/plain")
                    .asString();
        }
        catch(UnirestException e){
            return;
        }
        */

        /*
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.post("https://trailapi-trailapi.p.mashape.com/")
                    .header("accept", "text/plain")
                    .header("X-Mashape-Key", "7KsUvly5VOmsh9VDoPTll3vugdPVp1CPDGRjsnfyh3RQ3daUvm")
                    .field("q-city_cont", "Mission")
                    .field("q-country_cont", "United States")
                    .field("q-state_cont", "Texas")
                    .asJson();
        }
        catch (Exception e){
            System.out.println("Error in request");
        }
        *-/

       doInBackground();
    }

    protected String doInBackground(String... strings) {
        Log.i("SearchRequestHandler", "Starting background task. ");
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            //add all three URL string's together to form the entire required URL
            URL url = new URL(this.baseurl + this.midurl + this.endurl);
            Log.d("URL", url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream in = connection.getInputStream(); //get JSON Response from URL.
            if (in == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(in));
            String JSONString = getJSonStringFromBuffer(reader);//we keep the JSON in a string format. The results class will parse the data.
            Log.i("JSON Return", JSONString);
            return JSONString;

        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        } finally {
            if (connection != null)
                connection.disconnect();
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException io) {
                    Log.e("Reader Error", "Reader Closing Error.");
                    return null;
                }
        }
        Log.i("SearchRequestHandler", "Ending background task. ");
        return null;
    }
    private String getJSonStringFromBuffer(BufferedReader br) throws Exception {
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line + "\n");
        }
        if (buffer.length() == 0) {
            return null;
        }
        return buffer.toString();
    }
}
*/


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joe Passanante on 3/1/2018.
 * This class will process search requests for locations using the trailAPI.
 */

public class Request extends AsyncTask<String, Integer, String> {
    static interface RequestCallback {
        void Callback(String JSONResult);
    }

    private ProgressBar progressBar;
    private String country, state, city;
    private int radius;
    private long time;
    private final static String baseurl = "https://trailapi-trailapi.p.mashape.com/";
    //private final static String endurl = "&mashape-key=pKccbSem6FmshfKcq5kMbN60uwHgp1dMFkPjsnEvm8ubvA7hjD";
    private final static String endurl = "&mashape-key=7KsUvly5VOmsh9VDoPTll3vugdPVp1CPDGRjsnfyh3RQ3daUvm";
    private String midurl;
    private RequestCallback callBackObject;

    /**
     * @param country  - The country to search within
     * @param state    - The state within the country to search in
     * @param city     - The city to target
     * @param radius   - The radius around the city (Miles)
     * @param callback - The method to call with the callback data. This is critical to extract the data out of the process.
     * @param progress - Progress bar to pass search data to(Does not get properly called, left in for future development perhaps).
     */
    public Request(String country, String state, String city, int radius, RequestCallback callback, ProgressBar progress) {
        //initialize strings if there are values given.
        this.progressBar = progress;
        this.callBackObject = callback;
        this.country = country;
        this.city = city;
        this.state = state;
        this.radius = (radius <= 200) ? radius : 200; //keep radius at 200 miles max || Through testing, this parameter seems to be irrelevant for the API, however keeping due to lack of documentation.
        Log.i("SearchRequestHandler", "We have gotten a request, processing now.");
        //build the data containing url that we will be using to get JSON data.
        midurl = "?q[city_cont]=" + this.city + "&q[country_cont]=" + this.country + "&q[state_cont]=" + this.state + "&radius=" + this.radius + "";
    }

    /**
     * Secondary constructor to give option of not having a progress bar.
     *
     * @param country - The country to search within
     * @param state   - The state within the country to search in
     * @param city    - The city to target
     * @param radius  - The radius around the city (Miles)
     * @param callback - The method to call with the callback data. This is critical to extract the data out of the process.
     */
    public Request(String country, String state, String city, int radius, RequestCallback callback) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.radius = radius;
        this.callBackObject = callback;
    }

    @Override
    //will not be using
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    //The return of doInBackground gets passed here.
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Once we have our results, we need to pass it to the results activity
        if (result == null || result.isEmpty())
            return;
        Log.i("SearchRequestHandler", "Returning results through callback function. ");
        this.callBackObject.Callback(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (this.progressBar != null) {
            this.progressBar.setProgress(values[0]);
            Log.i("Progress", String.valueOf(values[0]));
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.i("SearchRequestHandler", "Starting background task. ");
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            //add all three URL string's together to form the entire required URL
            URL url = new URL(this.baseurl + this.midurl + this.endurl);
            Log.d("URL", url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream in = connection.getInputStream(); //get JSON Response from URL.
            if (in == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(in));
            String JSONString = getJSonStringFromBuffer(reader);//we keep the JSON in a string format. The results class will parse the data.
            Log.i("JSON Return", JSONString);
            return JSONString;

        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        } finally {
            if (connection != null)
                connection.disconnect();
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException io) {
                    Log.e("Reader Error", "Reader Closing Error.");
                    return null;
                }
        }
        Log.i("SearchRequestHandler", "Ending background task. ");
        return null;
    }

    private String getJSonStringFromBuffer(BufferedReader br) throws Exception {
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line + "\n");
        }
        if (buffer.length() == 0) {
            return null;
        }
        return buffer.toString();
    }
}
