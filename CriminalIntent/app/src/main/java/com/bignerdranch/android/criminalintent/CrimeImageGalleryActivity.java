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
        Crime crime = new Crime();
        Intent intent = CrimePagerActivity.newIntent(CrimeImageGalleryActivity.this, ((UUID) getIntent().getSerializableExtra("CRIME_ID")));
        startActivity(intent);

        return true;

    }
}
