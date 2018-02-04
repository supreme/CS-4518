package com.example.c4ll3.project3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView fuller_visits;
    private int fuller_counter = 0;

    private TextView library_visits;
    private int library_counter = 0;

    private ImageView activity_image;
    private TextView text_activity;
    private String activity = "Still";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fuller_visits = findViewById(R.id.text_fuller);
        library_visits = findViewById(R.id.text_library);
        text_activity = findViewById(R.id.text_activity);

        fuller_visits.setText(getString(R.string.visits_to_fuller_labs_geofence, fuller_counter));
        library_visits.setText(getString(R.string.visits_to_library_geofence, library_counter));
        text_activity.setText(getString(R.string.you_are, activity));
    }
}
