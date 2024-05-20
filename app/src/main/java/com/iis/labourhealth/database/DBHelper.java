package com.iis.labourhealth.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {


    // <----- Database Creation Area ----->

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dolDatabase";

    // Create Database Statement
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // <----- Route Main Table Area ----->

    // Route Main Table Name
    public static final String DOL_TABLE_NAME = "dolTable";

    // Route Main Table Columns names
    public static final String KEY_DOL_ID = "dolId";
    public static final String KEY_DOL_NAME = "dolName";
    public static final String KEY_DR_NAME = "drName";
    public static final String KEY_DOL_EMAIL = "dolEmail";
    public static final String KEY_DOL_PHONE = "dolPhone";
    public static final String KEY_DOL_ADDRESS = "dolAddress";
    public static final String KEY_DOL_DIVISION = "dolDivision";
    public static final String KEY_DOL_LATITUDE = "dolLatitude";
    public static final String KEY_DOL_LONGITUDE = "dolLongitiude";


    // Route Main Table Information
    public String CREATE_DOL_TABLE = "create table " + DOL_TABLE_NAME + "("
            + KEY_DOL_ID + " integer primary key autoincrement, "
            + KEY_DOL_NAME + " text not null, "
            + KEY_DR_NAME + " text not null, "
            + KEY_DOL_EMAIL + " text not null, "
            + KEY_DOL_PHONE + " text not null, "
            + KEY_DOL_ADDRESS + " text not null, "
            + KEY_DOL_DIVISION + " text not null, "
            + KEY_DOL_LATITUDE + " text not null, "
            + KEY_DOL_LONGITUDE + " text not null);";


    // <----- Database Table Creation Statement ----->
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DOL_TABLE); // DOL table Creation
    }

    // <----- Database Table Updating Statement ----->
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data ");

        db.execSQL("DROP TABLE IF EXISTS " + DOL_TABLE_NAME); // DOL Table
        onCreate(db);
    }
}
