package com.findchildren.avi.test.utils;

import android.util.Log;

/**
 * Created by Avi on 24.09.2017.
 */

public class LogUtil {
    public static void log(String tag, String log){
        Log.d(tag, log);
    }

    public static void log(Object object, String log){
        Log.d(object.getClass().getSimpleName(), log);
    }
}
