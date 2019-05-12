package com.mathgame.plugin.sudoku.ui;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class structure taken from tutorial at http://www.androidhive.info/2016/05/android-build-intro-slider-app/
 */
class PrefManager {
    // Shared preferences file name
    private static final String                   PREF_NAME = "androidhive-welcome";
    private static final String                   IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private final        SharedPreferences        pref;
    private final        SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        // shared pref mode
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

}