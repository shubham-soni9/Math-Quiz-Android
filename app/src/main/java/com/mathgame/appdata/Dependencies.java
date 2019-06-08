package com.mathgame.appdata;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.mathgame.R;
import com.mathgame.model.CustomMode;
import com.mathgame.model.GameResult;
import com.mathgame.model.Settings;
import com.mathgame.util.Prefs;

import java.util.Locale;

public class Dependencies {
    public static Locale getLocale(Context context) {
        return new Locale(getLanguageCode(context));
    }

    public static String getLanguageCode(Context context) {
        return Prefs.with(context).getString(Keys.Prefs.KEY_LOCALE, "en");
    }

    public static void setLanguage(Context context, String code) {
        Prefs.with(context).save(Keys.Prefs.KEY_LOCALE, code);
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

    public static void setGameResult(Context context, GameResult gameResult) {
        Prefs.with(context).save(Keys.Prefs.GAME_RESULTS, gameResult);
    }

    public static GameResult getGameResult(Context context) {
        return Prefs.with(context).getObject(Keys.Prefs.GAME_RESULTS, GameResult.class);
    }
}
