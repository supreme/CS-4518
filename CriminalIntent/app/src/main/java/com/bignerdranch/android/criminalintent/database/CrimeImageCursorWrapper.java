package com.bignerdranch.android.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;


import com.bignerdranch.android.criminalintent.CrimeImage;

public class CrimeImageCursorWrapper extends CursorWrapper {
    public CrimeImageCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public CrimeImage getCrimeImage() {
        String uuid = getString(getColumnIndex(CrimeDbSchema.CrimeImageTable.Cols.UUID));
        String identifier = getString(getColumnIndex(CrimeDbSchema.CrimeImageTable.Cols.IDENTIFIER));

        return new CrimeImage(uuid, identifier);
    }
}
