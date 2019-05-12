package com.mathgame.appdata;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.mathgame.R;
import com.mathgame.model.Settings;
import com.mathgame.util.Prefs;

import java.util.Locale;

public class Dependencies {
    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static void saveSettings(Context context, Settings settings) {
        Prefs.with(context).save(Keys.Prefs.KEY_SETTINGS, settings);
    }

    public static Settings getSettings(Context context) {
        return Prefs.with(context).getObject(Keys.Prefs.KEY_SETTINGS, Settings.class);
    }

    public static void saveThemeColor(Context context, int color) {
        Prefs.with(context).save(Keys.Prefs.KEY_SETTINGS, color);
    }

    public static int getThemeColor(Context context) {
        return Prefs.with(context).getInt(Keys.Prefs.KEY_SETTINGS, ContextCompat.getColor(context, R.color.colorPrimary));
    }

    public static void setFirstTimeSudokuLaunch(Context context, boolean value) {
        Prefs.with(context).save(Keys.Prefs.KEY_SETTINGS, value);
    }

    public static boolean isFirstTimeSudokuLaunch(Context context) {
        return Prefs.with(context).getBoolean(Keys.Prefs.KEY_SETTINGS, true);
    }
}
