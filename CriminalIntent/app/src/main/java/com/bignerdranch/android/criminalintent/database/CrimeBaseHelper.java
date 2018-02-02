package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeImageTable;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "CrimeBaseHelper";
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SUSPECT + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.THUMBNAIL +
                ")"
        );

        db.execSQL("create table " + CrimeImageTable.NAME + "(" +
                CrimeImageTable.Cols.UUID + ", " +
                CrimeImageTable.Cols.IDENTIFIER + ", " +
                " FOREIGN KEY (" + CrimeImageTable.Cols.UUID + ") REFERENCES "
                    + CrimeTable.NAME + " (" + CrimeTable.Cols.UUID + ")" +
                ")"
        );

        Log.d("Database", "onCreate called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
