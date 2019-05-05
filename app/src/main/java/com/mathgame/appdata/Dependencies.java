package com.mathgame.appdata;

import android.content.Context;

import com.mathgame.model.Settings;
import com.mathgame.util.Prefs;

import java.util.Locale;

public class Dependencies {
    public static Locale getLocale(Context context) {
        return Locale.getDefault();
    }


    public static void saveSettings(Context context, Settings settings) {
        Prefs.with(context).save(Keys.Prefs.KEY_SETTINGS, settings);
    }

    public static Settings getSettings(Context context) {
        return Prefs.with(context).getObject(Keys.Prefs.KEY_SETTINGS, Settings.class);
    }
}
