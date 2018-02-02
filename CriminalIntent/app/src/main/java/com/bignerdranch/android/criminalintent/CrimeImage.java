package com.bignerdranch.android.criminalintent;

/**
 * Created by sandrews on 2/1/18.
 */

public class CrimeImage {
    private String uuid;
    private String identifier;

    public CrimeImage(String uuid, String identifier) {
        this.uuid = uuid;
        this.identifier = identifier;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}