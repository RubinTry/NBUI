package cn.rubintry.nbui.core;

import android.util.Log;

public class UPluginLogger {

    private static final String TAG = "UPluginLogger";
    public static void d(String message){
        Log.d(TAG, message);
    }

    public static void e(Exception e){
        String stackTraceString = Log.getStackTraceString(e);
        Log.e(TAG, stackTraceString);
    }
}
