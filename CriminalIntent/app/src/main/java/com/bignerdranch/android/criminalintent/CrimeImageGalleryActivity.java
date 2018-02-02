package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import java.util.UUID;

public class CrimeImageGalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        UUID crimeId = (UUID) getIntent().getSerializableExtra("CRIME_ID");
        return CrimeImageGallery.newInstance(crimeId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent oldIntent = getIntent();
        Intent intent = new Intent(CrimeImageGalleryActivity.this, CrimePagerActivity.class);
        startActivity(intent);

        return true;

    }
}
