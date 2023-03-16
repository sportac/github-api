package com.example.githubapi.util;

import android.util.Log;

import com.example.githubapi.BuildConfig;

public class LogUtil {

    // Debug logs
    public static void debug(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    // Error logs
    public static void error(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

}
