package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

public class CrimeImageGalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){ return new CrimeImageGallery();}

    public boolean onOptionsItemSelected(MenuItem item){
        Intent oldIntent = getIntent();
        Intent intent = new Intent(CrimeImageGalleryActivity.this, CrimePagerActivity.class);
        startActivity(intent);

        return true;

    }
}
