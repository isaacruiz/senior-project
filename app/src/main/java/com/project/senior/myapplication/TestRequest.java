package com.project.senior.myapplication;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

public class TestRequest extends AsyncTask<String, String, String> {

    //Callback interface
    static interface RequestHandlerCallback {
        void Callback(String JSONResult);
    }


    private String city;
    private String state;
    private String country;
    private String endpoint = "https://trailapi-trailapi.p.mashape.com/";
    private String query;
    private String key = "&mashape-key=7KsUvly5VOmsh9VDoPTll3vugdPVp1CPDGRjsnfyh3RQ3daUvm";
    private RequestHandlerCallback callBackObject;

    public TestRequest(String city, String state, String country){
        this.city = city;
        this.state = state;
        this.country = country;
        query = "?q[city_cont]=" + city + "&q[country_cont]=" + country + "&q[state_cont]=" + state + "&radius=200";
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            //Setup connection
            URL url = new URL(endpoint + query + key);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Read result from API request
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String value = getJSonStringFromBuffer(br);
            JSONObject json = new JSONObject(value);
            String json_string = json.toString();
            System.out.println("This is the result: " + json_string);

            return value;
        }

        catch(Exception e){
            return (e.getMessage());
        }
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
