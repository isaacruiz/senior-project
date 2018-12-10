package com.project.senior.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrailData {

    //Builds trail data from
    private String name;
    private double latitude;
    private double longitude;
    private JSONArray activities;

    //Constructor
    public TrailData(JSONObject obj){

        try {
            latitude = obj.getDouble("lat");
            longitude = obj.getDouble("lon");
            name = obj.getString("name");
            activities = obj.getJSONArray("activities");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getActivitiesString() {
        String s = "Activities:\n";
        JSONObject activity;
        for (int i = 0; i < activities.length(); i++) {
            try {
                activity = activities.getJSONObject(i);
                s +=  "Type:" + activity.getString("activity_type_name") + "\n";
                s += "Desc:" + activity.getString("description") + "\n";
                s += "Length: " + activity.getInt("length") + "miles\n\n";
                return s;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "Failed to convert data to trail data string!!";
    }

    public String toString(){
        return name + " " + latitude + ", " + longitude + "\n" + getActivitiesString();
    }
}
