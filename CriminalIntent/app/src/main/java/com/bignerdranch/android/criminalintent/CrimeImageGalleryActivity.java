package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class CrimeImageGalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){ return new CrimeImageGallery();}
}
