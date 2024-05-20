package com.iis.labourhealth.util;

import android.content.Context;
import android.widget.Toast;

public class Functions {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
