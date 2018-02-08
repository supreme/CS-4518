package com.example.c4ll3.project3;

/**
 * Created by Patrick I Critz on 2/7/2018.
 */

public class GeofenceStructure {
    public String geofenceID;
    public double latitude;
    public double longitude;
    public int radius;

    public GeofenceStructure(String id, double lat, double lng, int rad){
        this.geofenceID = id;
        this.latitude = lat;
        this.longitude = lng;
        this.radius = rad;
    }
}
