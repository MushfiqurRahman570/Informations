package com.iis.labourhealth.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.iis.labourhealth.model.DOLModel;

import java.util.ArrayList;

public class DOLDataSource {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DOLDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean addDOL(DOLModel dolModel) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_DOL_NAME, dolModel.getmDOLName());
        values.put(DBHelper.KEY_DR_NAME, dolModel.getmDrName());
        values.put(DBHelper.KEY_DOL_EMAIL, dolModel.getmDOLEmail());
        values.put(DBHelper.KEY_DOL_PHONE, dolModel.getmDOLPhone());
        values.put(DBHelper.KEY_DOL_ADDRESS, dolModel.getmDOLAddress());
        values.put(DBHelper.KEY_DOL_DIVISION, dolModel.getmDOLDivision());
        values.put(DBHelper.KEY_DOL_LATITUDE, dolModel.getmDOLLatitude());
        values.put(DBHelper.KEY_DOL_LONGITUDE, dolModel.getmDOLLongitude());

        long inserted = db.insert(DBHelper.DOL_TABLE_NAME, null, values);

        this.close();
        if (inserted < 0) {
            return false;
        } else {
            return true;
        }
    }


    // Getting Single DOL by Id
    public DOLModel getSingleDOLById(String eWsmsId) {
        DOLModel dolModel = null;
        open();

        String selectQuery = "SELECT * FROM " + DBHelper.DOL_TABLE_NAME
                + " WHERE " + DBHelper.KEY_DOL_ID + "='" + eWsmsId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String mDOLName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_NAME));
                String mDrName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DR_NAME));
                String mDOLEmail = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_EMAIL));
                String mDOLPhone = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_PHONE));
                String mDOLAddress = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_ADDRESS));
                String mDOLLatitude= cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_LATITUDE));
                String mDOLLongitude = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_LONGITUDE));

                dolModel = new DOLModel(mDOLName,mDrName, mDOLEmail, mDOLPhone, mDOLAddress, mDOLLatitude, mDOLLongitude);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return dolModel;
    }


    // Getting All DOL in a ArrayList by a Division Name
    public ArrayList<DOLModel> getAllDOLListByDivision(String eDivisionName) {
        ArrayList<DOLModel> mAllDOlListByDivision = new ArrayList<DOLModel>();
        open();

        String selectQuery = "SELECT * FROM " + DBHelper.DOL_TABLE_NAME
                + " WHERE " + DBHelper.KEY_DOL_DIVISION + "='" + eDivisionName + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String mDOLId = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_ID));
                String mDOLName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_NAME));
                DOLModel dolModel = new DOLModel(Integer.parseInt(mDOLId), mDOLName);
                mAllDOlListByDivision.add(dolModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        // Return All DOL ArrayList By Division Name
        return mAllDOlListByDivision;
    }

    // Getting All DOL in a ArrayList
    public ArrayList<DOLModel> getAllDOLList() {
        ArrayList<DOLModel> mAllDOLList = new ArrayList<DOLModel>();
        open();
        Cursor cursor = db.query(DBHelper.DOL_TABLE_NAME, null, null, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String mDOLName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_NAME));
                String mDrName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DR_NAME));
                String mDOLEmail = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_EMAIL));
                String mDOLPhone = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_PHONE));
                String mDOLAddress = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_ADDRESS));
                String mDOLLatitude= cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_LATITUDE));
                String mDOLLongitude = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DOL_LONGITUDE));

                DOLModel mDOL = new DOLModel(mDOLName, mDrName, mDOLEmail, mDOLPhone, mDOLAddress, mDOLLatitude, mDOLLongitude);
                mAllDOLList.add(mDOL);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        // Return All DOL ArrayList
        return mAllDOLList;
    }


    // Check isEmpty
    public boolean isEmpty() {
        this.open();
        Cursor cursor = db.query(DBHelper.DOL_TABLE_NAME, new String[] {
                        DBHelper.KEY_DOL_ID,
                        DBHelper.KEY_DOL_NAME,
                        DBHelper.KEY_DR_NAME,
                        DBHelper.KEY_DOL_ADDRESS,
                        DBHelper.KEY_DOL_EMAIL,
                        DBHelper.KEY_DOL_PHONE,
                        DBHelper.KEY_DOL_LATITUDE,
                        DBHelper.KEY_DOL_LONGITUDE }, null,
                null, null, null, null);

        if (cursor.getCount() == 0) {
            this.close();
            return true;
        } else {
            this.close();
            return false;
        }
    }

}
