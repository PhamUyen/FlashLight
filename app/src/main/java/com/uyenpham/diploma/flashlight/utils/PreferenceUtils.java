package com.uyenpham.diploma.flashlight.utils;

import android.content.Context;


public class PreferenceUtils {
    private static final String PREF_NAME = "flashLight";

    public static void saveBoolean(Context context, String key, boolean value) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public static void saveInt(Context context, String key, int value) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    public static void saveString(Context context, String key, String value) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(key, false);
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }
    public static int getInt(Context context, String key, int defValue) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt(key, defValue);
    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString(key, "");
    }
    public static String getString(Context context, String key, String defValue) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString(key, defValue);
    }

    public static void remove(Context context, String key) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().remove(key).apply();
    }
}
