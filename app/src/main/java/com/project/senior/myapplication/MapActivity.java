package com.project.senior.myapplication;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MapActivity extends AppCompatActivity{
    private MapView mapView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiaXJ1aXoiLCJhIjoiY2ptNm10ejFjMDF4MDNwcXgwMHp2emNvNiJ9.zQIfjDgu8IQt4u9e7Z2wRA");
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                /*
                //Display GPS location on map
                try {
                    LocationComponent locationComponent = mapboxMap.getLocationComponent();
                    locationComponent.activateLocationComponent(this)
                }
                catch(SecurityException e){
                    Log.d("Security Error", e.getMessage());
                }
                */

                float[][] coordinates = {{26.303854f, -98.178339f}, {26.301924f, -98.165765f}, {26.315847f, -98.162577f}};
                drawPins(mapboxMap, coordinates); //Draw a set of coordinates
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    protected void drawPins(MapboxMap map, float[][] coordinates){
        for (float[] coordinate: coordinates) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(coordinate[0], coordinate[1]))
                    .title("Test Marker"));
        }

    }
}
