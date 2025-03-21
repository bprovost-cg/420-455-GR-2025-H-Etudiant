package com.example.fragments;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class PreferenceUtils {
    public static final String KEY_DARK_THEME = "dark_theme";

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void onSharedPreferenceChanged(Context context, String key) {
        switch (key) {
            case KEY_DARK_THEME:
                applyTheme(context);
                break;
        }
    }

    public static void applyTheme(Context context) {
        boolean isDarkThemeEnabled = getSharedPreferences(context).getBoolean(KEY_DARK_THEME, false);
        int nightMode = isDarkThemeEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }
}
