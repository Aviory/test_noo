package com.findchildren.avi.test.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Avi on 23.09.2017.
 */

public class Prefs {
    private static final String TEST_APP_PREF = "test_pref";
    private static final String PREF_TOKEN = "preferences_token";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(TEST_APP_PREF, Context.MODE_PRIVATE);
    }
    public static void putString(Context context, String preferenceKey, String preferenceValue) {
        getPrefs(context).edit().putString(preferenceKey, preferenceValue).commit();
    }
    public static String getString(Context context, String preferenceKey, String preferenceDefaultValue) {
        return getPrefs(context).getString(preferenceKey, preferenceDefaultValue);
    }

    public static void putToken(Context context, String token) {
        putString(context, PREF_TOKEN, token);
    }

    public static String getToken(Context context) {
        return getString(context, PREF_TOKEN, null);
    }

    public static void clearPrefs(Context context) {
        getPrefs(context).edit().clear().apply();
    }
}
