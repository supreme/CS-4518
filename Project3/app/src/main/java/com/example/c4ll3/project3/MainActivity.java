package com.example.c4ll3.project3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SensorEventListener {

    public static final String TAG = "MainActivity";

    // Google Play Services API client
    private GoogleApiClient apiClient;

    // Activity monitoring and messaging
    private ActivityMonitor activityMonitor;
    private Handler activityHandler;

    private TextView fuller_visits;
    private int fuller_counter = 0;

    private TextView library_visits;
    private int library_counter = 0;

    private ImageView activity_image;
    private TextView text_activity;
    private String activity = "Still";

    // Map
    private GoogleMap mMap;
    private LocationManager locationManager;
    private String bestLocationProvider;
    private static Criteria criteria;
    private static final int DEFAULT_ZOOM = 17; // Higher value, higher zoom
    private final int MIN_TIME = 3000;     // Minimum time between updates in milliseconds
    private final int MIN_DISTANCE = 3;    // Minimum distance between updates in meters

    // Pedometer
    private SensorManager mSensorManager;
    private Sensor mSensor;
    public static int stepCount = 0;
    private final int STEP_COUNT_IN_GEOFENCE = 6;
    public static boolean inFullerGeofence = false;
    public static boolean inLibraryGeofence = false;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GeofencingClient mGeofencingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and start map, for making changes to map functionality see onMapReady
        checkPermissions();
        final MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mapView.onResume();

        // Initialize Google Play Services
        initGooglePlayServices();

        // Initialize pedometer and begin listening for steps
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        }

        // Get UI elements and fill with default values
        activity_image = findViewById(R.id.acitivty_image);
        fuller_visits = findViewById(R.id.text_fuller);
        library_visits = findViewById(R.id.text_library);
        text_activity = findViewById(R.id.text_activity);

        fuller_visits.setText(getString(R.string.visits_to_fuller_labs_geofence, fuller_counter));
        library_visits.setText(getString(R.string.visits_to_library_geofence, library_counter));
        text_activity.setText(getString(R.string.you_are, activity));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        startLocationMonitoring();
        startGeofenceMonitoring();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        stopGeofenceMonitoring();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (inFullerGeofence){
            Log.d(TAG, "In fuller geofence");
            stepCount++;
            if (stepCount == STEP_COUNT_IN_GEOFENCE){
                fuller_counter++;
                fuller_visits.setText(getString(R.string.visits_to_fuller_labs_geofence, fuller_counter));
                Toast.makeText(this, "You have taken 6 steps inside Fuller Labs Geofence, incrementing counter", Toast.LENGTH_SHORT).show();
            }
        }
        else if (inLibraryGeofence) {
            Log.d(TAG, "In library geofence");
            stepCount++;
            if (stepCount == STEP_COUNT_IN_GEOFENCE){
                library_counter++;
                library_visits.setText(getString(R.string.visits_to_library_geofence, library_counter));
                Toast.makeText(this, "You have taken 6 steps inside the Gordon Library Geofence, incrementing counter", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;

        checkPermissions();

        MapsInitializer.initialize(MainActivity.this);
        mMap.setMyLocationEnabled(true);
        initLocationManager();

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationManager.requestLocationUpdates(bestLocationProvider, MIN_TIME, MIN_DISTANCE, this);
                Location current = locationManager.getLastKnownLocation(bestLocationProvider);
                LatLng currentLatLong = new LatLng(current.getLatitude(), current.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, DEFAULT_ZOOM));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(bestLocationProvider, 0, 0, locationListener);

        // Sets GPS's last known location as default for when map is created
        Location lastKnown = locationManager.getLastKnownLocation(bestLocationProvider);
        if (lastKnown != null) {
            LatLng defaultLatLong = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLong, DEFAULT_ZOOM));
        }
    }

    // Check if user has given us location access, if not ask them for it
    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    private void initLocationManager(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Determine which services are available to use for location services
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            Toast.makeText(getApplicationContext(), "Unable to use location services!", Toast.LENGTH_LONG).show();
        }

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true);

        // Get best provider from available selection
        bestLocationProvider = locationManager.getBestProvider(criteria, true);
    }


    /**
     * ----------------------------------------
     * Begin section for Google Play Services.
     * ----------------------------------------
     */

    /**
     * Initialize Google Play Services API client.
     */
    private synchronized void initGooglePlayServices() {
        apiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        apiClient.connect();

        activityMonitor = new ActivityMonitor();
        activityHandler = new Handler(new Handler.Callback() {  // Callback handler for activity recognition service
            @Override
            public boolean handleMessage(Message msg) {
                String activity = msg.getData().getString(Constants.ACTIVITY_MESSAGE_TAG);

                if (activityMonitor.needsUpdate(activity)) {
                    int timeSpent = activityMonitor.getActivityDuration();
                    text_activity.setText(getString(R.string.you_are, activityMonitor.getCurrentActivity()));

                    // Set image view based on activity
                    if (activity.equals(getString(R.string.activity_still))) {
                        activity_image.setImageDrawable(getResources().getDrawable(R.drawable.still));
                    } else if (activity.equals(getString(R.string.activity_walking))) {
                        activity_image.setImageDrawable(getResources().getDrawable(R.drawable.walking));
                    } else if (activity.equals(getString(R.string.activity_running))) {
                        activity_image.setImageDrawable(getResources().getDrawable(R.drawable.running));
                    }

                    if (timeSpent > 0) {
                        Toast.makeText(getApplicationContext(),
                                "You were " + activityMonitor.getPreviousActivity() + " for " + timeSpent + " seconds",
                                Toast.LENGTH_LONG).show();
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(this, ActivityRecognizedService.class);
        intent.putExtra("messenger", new Messenger(activityHandler));
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognitionClient activityRecognitionClient = ActivityRecognition.getClient(getApplicationContext());
        activityRecognitionClient.requestActivityUpdates(Constants.ACTIVITY_CHECK_DELAY, pendingIntent);

        startLocationMonitoring();
        startGeofenceMonitoring();
        Log.d(TAG, "onConnected() api client connected: " + apiClient.isConnected());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void startLocationMonitoring() {
        Log.d(TAG, "startLocation called");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            LocationRequest locationRequest = LocationRequest.create()
                    .setInterval(10000)
                    .setFastestInterval(5000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback(), null);
        } catch (SecurityException e){
            Log.d(TAG, "SecurityException - " + e.getMessage());
        }
    }

    private void startGeofenceMonitoring() {
        mGeofencingClient = LocationServices.getGeofencingClient(this);
        Log.d(TAG, "" + Constants.LANDMARKS.size());
        try{
            List<Geofence> geofenceList = new ArrayList<>();
            for(String k : Constants.LANDMARKS.keySet()){
                Geofence tempGeo = new Geofence.Builder()
                        .setRequestId(Constants.LANDMARKS.get(k).geofenceID)
                        .setCircularRegion(Constants.LANDMARKS.get(k).latitude, Constants.LANDMARKS.get(k).longitude, Constants.LANDMARKS.get(k).radius)
                        .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                        .setNotificationResponsiveness(1000)//in milliseconds
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                        .build();
                geofenceList.add(tempGeo);
                Log.d(TAG, "Created Geofence" + Constants.LANDMARKS.get(k).geofenceID);
            }

            GeofencingRequest geofenceRequest = new GeofencingRequest.Builder()
                    .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    .addGeofences(geofenceList)
                    .build();

            Intent intent = new Intent(this, GeofenceService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if(!apiClient.isConnected()){
                Log.d(TAG, "GoogleApiClient Is not connected");
            } else {
                mGeofencingClient.addGeofences(geofenceRequest, pendingIntent);
            }
        } catch (SecurityException e){
            Log.d(TAG, "SecurityException - " + e.getMessage());
        }
    }

    private void stopGeofenceMonitoring(){
        Log.d(TAG, "stopMonitoring called");
        ArrayList<String> geofenceIds = new ArrayList<String>();
        for(String k : Constants.LANDMARKS.keySet()){
            geofenceIds.add(Constants.LANDMARKS.get(k).geofenceID);
        }
        mGeofencingClient.removeGeofences(geofenceIds);
    }

}
