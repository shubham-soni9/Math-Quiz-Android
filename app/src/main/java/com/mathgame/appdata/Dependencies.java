package com.mathgame.appdata;

import android.content.Context;

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

    public static void setFirstTimeSudokuLaunch(Context context, boolean value) {
        Prefs.with(context).save(Keys.Prefs.KEY_SUDOKU_FIRST_LAUNCH, value);
    }

    public static boolean isFirstTimeSudokuLaunch(Context context) {
        return Prefs.with(context).getBoolean(Keys.Prefs.KEY_SUDOKU_FIRST_LAUNCH, true);
    }

    public static void setSinglePlayerResult(Context context, GameResult gameResult) {
        Prefs.with(context).save(Keys.Prefs.FIRST_PLAYER_RESULT, gameResult);
    }

    public static GameResult getSinglePlayerResult(Context context) {
        return Prefs.with(context).getObject(Keys.Prefs.FIRST_PLAYER_RESULT, GameResult.class);
    }

    public static void setSecondPlayerResult(Context context, GameResult gameResult) {
        Prefs.with(context).save(Keys.Prefs.SECOND_PLAYER_RESULT, gameResult);
    }

    public static GameResult getSecondPlayerResult(Context context) {
        return Prefs.with(context).getObject(Keys.Prefs.SECOND_PLAYER_RESULT, GameResult.class);
    }
}
