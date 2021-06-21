package com.idat.almacen.core.util;

import android.util.Log;

import androidx.annotation.Nullable;

public final class Console {

    private static String TAG = "Console";

    public static void log(@Nullable Object obj) {
        Log.d(TAG, String.format("%s: %s", obj.getClass().getName(), obj.toString()));
    }
}
