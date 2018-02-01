package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeCursorWrapper;

import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeImageTable;
import com.bignerdranch.android.criminalintent.database.CrimeImageCursorWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
    }


    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void addCrimeImage(CrimeImage image) {
        ContentValues values = new ContentValues();
        values.put(CrimeImageTable.Cols.UUID, image.getUuid());
        values.put(CrimeImageTable.Cols.IDENTIFIER, image.getIdentifier());

        mDatabase.insert(CrimeImageTable.NAME, null, values);
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            crimes.add(cursor.getCrime());
            cursor.moveToNext();
        }
        cursor.close();

        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            Crime crime = cursor.getCrime();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    /**
     * Returns a list of all images associated with a crime.
     * @param crime The crime to get the images for.
     * @return A list of crime images.
     */
    public List<CrimeImage> getCrimeImages(Crime crime) {
        List<CrimeImage> crimeImages = new ArrayList<>();
        CrimeImageCursorWrapper cursor = queryCrimeImages(
                CrimeImageTable.Cols.UUID + " = ?",
                new String[] { crime.getId().toString() }
        );

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimeImages.add(cursor.getCrimeImage());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return crimeImages;
    }

    public File getCrimePhoto(Crime crime) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }

        CrimeImage crimeImage = new CrimeImage(crime.getId().toString(), crime.getPhotoFilename());
        addCrimeImage(crimeImage);
        return new File(externalFilesDir, crimeImage.getIdentifier());
    }

    public File getCrimeThumbnail(Crime crime) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }

        if (crime.getThumbnail() == null) {
            crime.setThumbnail(crime.getPhotoFilename());
            addCrimeImage(new CrimeImage(crime.getId().toString(), crime.getThumbnail()));
        }

        return new File(externalFilesDir, crime.getThumbnail());
    }

    /**
     * Creates a new file object to store the {@link CrimeImage} on the file system.
     * @param image The {@link CrimeImage} to store.
     * @return The {@link File} object to store.
     */
    public File getCrimePhotoFile(CrimeImage image) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }

        return new File(externalFilesDir, image.getIdentifier());
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        values.put(CrimeTable.Cols.THUMBNAIL, crime.getThumbnail());

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new CrimeCursorWrapper(cursor);
    }

    private CrimeImageCursorWrapper queryCrimeImages(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeImageTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CrimeImageCursorWrapper(cursor);
    }
}
