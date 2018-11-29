package com.project.senior.myapplication;

import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class TestRequest {

    // HTTP GET request
    public String sendGet() throws Exception {

        String url = "https://trailapi-trailapi.p.mashape.com/?q[city_cont]=Mission&q[country_cont]=United%20States&q[state_cont]=Texas&radius=200&mashape-key=7KsUvly5VOmsh9VDoPTll3vugdPVp1CPDGRjsnfyh3RQ3daUvm";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");


            int responseCode = con.getResponseCode();
            Log.d("Request","\nSending 'GET' request to URL : " + url);
            Log.d("Request","Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            SystemClock.sleep(7000);

            return response.toString();
        }
        catch(Exception e){
            return e.getMessage();
        }
    }
}
