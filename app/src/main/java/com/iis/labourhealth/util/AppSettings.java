package com.iis.labourhealth.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSettings {

    private Context mContext;
    private static AppSettings appSettingSingleton = null;
    private SharedPreferences prefs;


    private AppSettings(Context _mContext) {
        this.mContext = _mContext;
        prefs = PreferenceManager.getDefaultSharedPreferences(this.mContext);
    }

    /* Static 'instance' method */
    public static AppSettings getInstance(Context _mContext) {
        return (appSettingSingleton == null ? appSettingSingleton = new AppSettings(
                _mContext) : appSettingSingleton);
    }


    // Get first time boot
    public boolean getFirstTimeBoot() {
        return prefs.getBoolean(AppConstants.FIRST_TIME_BOOT,false);
    }

    // Set first time boot
    public void setFirstTimeBoot(boolean val) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(AppConstants.FIRST_TIME_BOOT, val);
        editor.commit();
    }


}

