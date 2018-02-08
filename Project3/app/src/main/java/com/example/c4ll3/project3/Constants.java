package com.example.c4ll3.project3;
import java.util.HashMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by TedKim on 2/6/18.
 */

class Constants {

    private static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";

    static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    private static final long GEOFENCES_EXPIRATION_IN_HOURS = 12;

    static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCES_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    static final float GEOFENCE_RADIUS_IN_METERS = 1609;

    static final HashMap<String, GeofenceStructure> LANDMARKS = new HashMap<>();

    static {
        LANDMARKS.put("FullerLab", new GeofenceStructure("FullerGeofence", 42.275060, -71.806504, 20));

        LANDMARKS.put("Library", new GeofenceStructure("LibraryGeofence", 42.274230, -71.806354, 20));
    }
}
