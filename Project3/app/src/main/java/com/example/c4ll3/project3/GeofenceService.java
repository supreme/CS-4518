package com.example.c4ll3.project3;

import android.app.IntentService;
import android.content.Intent;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import android.util.Log;
import java.util.List;

/**
 * Created by Patrick I Critz on 2/7/2018.
 */

public class GeofenceService extends IntentService {

    public static final String TAG = "GeofenceService";

    public GeofenceService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        if(event.hasError()){
            //HANDLE ERROR
        } else {
            int transition = event.getGeofenceTransition();
            List<Geofence> geofences = event.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            String requestId = geofence.getRequestId();

            if(transition == Geofence.GEOFENCE_TRANSITION_ENTER){
                Log.d(TAG, "Entering geofence - " + requestId);
                MainActivity.inGeofence = true;
            } else if(transition == Geofence.GEOFENCE_TRANSITION_EXIT){
                Log.d(TAG, "Exiting geofence - " + requestId);
                MainActivity.inGeofence = false;
            }
        }
    }
}
