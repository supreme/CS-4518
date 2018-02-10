package com.example.c4ll3.project3;
import java.util.HashMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by TedKim on 2/6/18.
 */

class Constants {
    static final int ACTIVITY_CHECK_DELAY = 0; // Check for activity as frequently as possible
    static final int ACTIVITY_CONFIDENCE_THRESHOLD = 50;
    static final String ACTIVITY_MESSAGE_TAG = "ACTIVITY";

    private static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";

    private static final long GEOFENCES_EXPIRATION_IN_HOURS = 12;

    static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCES_EXPIRATION_IN_HOURS * 60 * 60 * 1000;

    static final HashMap<String, GeofenceStructure> LANDMARKS = new HashMap<>();

    static {
        LANDMARKS.put("FullerLab", new GeofenceStructure("FullerGeofence", 42.274991, -71.806411, 35));
        LANDMARKS.put("Library", new GeofenceStructure("LibraryGeofence", 42.274224, -71.806388, 35));
    }
}
