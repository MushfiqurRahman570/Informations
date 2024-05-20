package com.iis.labourhealth.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppsSettings {
    private static AppsSettings appsSettings = null;
    private Context mContext;
    private SharedPreferences sharedPreferences;

    private AppsSettings(Context context) {
        mContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static AppsSettings getAppsSettings(Context mContext) {
        if (appsSettings == null) {
            appsSettings = new AppsSettings(mContext);
        }
        return appsSettings;
    }

    public void setAllPermissonAllow(boolean mValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AppConstants.ALL_PARMISSION_ALLOW, mValue);
        editor.commit();
    }

    public boolean getAllPermissonAllow() {
        return sharedPreferences.getBoolean(AppConstants.ALL_PARMISSION_ALLOW, false);
    }


}
