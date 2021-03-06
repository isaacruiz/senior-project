package com.project.senior.myapplication;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestRequest extends AsyncTask<String, String, List<TrailData>> {

    private String city;
    private String state;
    private String country;
    private double latitude;
    private double longitude;
    private String endpoint = "https://trailapi-trailapi.p.mashape.com/";
    private String query;
    private String key = "&mashape-key=7KsUvly5VOmsh9VDoPTll3vugdPVp1CPDGRjsnfyh3RQ3daUvm";
    private RequestCallback callback;
    final String TAG = "Request";

    public TestRequest(String city, String state, String country, RequestCallback callback){
        this.city = city;
        this.state = state;
        this.country = country;
        this.callback = callback;
        query = "?q[city_cont]=" + city + "&q[country_cont]=" + country + "&q[state_cont]=" + state + "&radius=200";
    }
    public TestRequest(double lat, double lon, RequestCallback callback){
        this.city = city;
        this.state = state;
        this.country = country;
        this.latitude = lat;
        this.longitude = lon;
        this.callback = callback;
        //endpoint = endpoint + "/trails/explore";
        query = "?lat=" + latitude + "&lon=" + lon;
    }


    @Override
    protected List<TrailData> doInBackground(String... strings) {
        try {
            //Setup connection
            URL url = new URL(endpoint + query + key);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Read result from API request
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = getJSonStringFromBuffer(br);
            System.out.println("This is the result: " + result);

            //Convert JSON result to TrailData class
            JSONObject json = new JSONObject(result);
            JSONArray places = json.getJSONArray("places");
            List<TrailData> trails = new ArrayList<TrailData>();
            for(int i = 0; i < places.length(); i++){
                TrailData td = new TrailData(places.getJSONObject(i));
                System.out.println("Trail data results:");
                System.out.println(td.toString());
                trails.add(td);
            }
            //return result;
            return trails;
        }

        catch(Exception e){

            //return (e.getMessage());
            Log.i(TAG, "Error in request:" + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<TrailData> result) {
        callback.completedRequest(result);
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

    //Processes api request and returns a list of trail details, one entry per coordinate

}
