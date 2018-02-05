package com.example.c4ll3.project3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int DEFAULT_ZOOM = 17; // Higher value, higher zoom
    private final int MIN_TIME = 3000;     // Minimum time between updates in milliseconds
    private final int MIN_DISTANCE = 3;    // Minimum distance between updates in meters

    private TextView fuller_visits;
    private int fuller_counter = 0;

    private TextView library_visits;
    private int library_counter = 0;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private static Criteria criteria;

    private ImageView activity_image;
    private TextView text_activity;
    private String activity = "Still";

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

        // Get UI elements and fill with default values
        fuller_visits = findViewById(R.id.text_fuller);
        library_visits = findViewById(R.id.text_library);
        text_activity = findViewById(R.id.text_activity);

        fuller_visits.setText(getString(R.string.visits_to_fuller_labs_geofence, fuller_counter));
        library_visits.setText(getString(R.string.visits_to_library_geofence, library_counter));
        text_activity.setText(getString(R.string.you_are, activity));
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
        System.out.println("In  onMapReady");

        checkPermissions();

        MapsInitializer.initialize(MainActivity.this);
        mMap.setMyLocationEnabled(true);
        initLocationManager();

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true), MIN_TIME, MIN_DISTANCE, this);
                Location current = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
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

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        // Sets GPS's last known location as default for when map is created
        Location lastKnown = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        LatLng defaultLatLong = new LatLng(lastKnown.getLatitude(), lastKnown.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLong, DEFAULT_ZOOM));
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
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true);
    }



}
