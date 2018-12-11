package com.project.senior.myapplication;

import java.util.List;

//Callback to get json response from async activity
public interface RequestCallback {

    void completedRequest(List<TrailData> result);

}
