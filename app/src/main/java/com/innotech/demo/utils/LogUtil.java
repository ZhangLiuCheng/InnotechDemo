package com.innotech.demo.utils;

import android.util.Log;

import com.innotech.demo.BuildConfig;

/**
 * Created by sunyuqin on 16/8/22.
 */

public class LogUtil {

    private static String sTAG = "InnotechTag";

    public static void v(String message) {
        // adb shell setprop log.tag.<YOUR_LOG_TAG> <LEVEL>
        if (BuildConfig.DEBUG /* && Log.isLoggable(sTAG, Log.VERBOSE) */) {
            Log.v(sTAG, message);
        }
    }

    public static void i(String message) {
        Log.i(sTAG, message);
    }

    public static void d(String message) {
        Log.d(sTAG, message);
    }

    public static void e(String message) {
        Log.e(sTAG, message);
    }
}

