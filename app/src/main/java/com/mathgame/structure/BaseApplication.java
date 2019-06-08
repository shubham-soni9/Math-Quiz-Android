package com.mathgame.structure;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.mathgame.appdata.Constant;
import com.mathgame.appdata.Dependencies;
import com.mathgame.database.ObjectBox;
import com.mathgame.model.CustomMode;
import com.mathgame.model.Settings;
import com.mathgame.util.Utils;

public class BaseApplication extends MultiDexApplication {

    public static void initSettings(Context context, Settings settings) {
        if (settings == null) {
            settings = new Settings();
            CustomMode customMode = new CustomMode();
            customMode.setNumberOfQuestions(10);
            customMode.setTimerValue(20);
            customMode.setSkipNumbers(3);
            customMode.setDifficulty(Constant.DifficultyLevel.SMALL);
            settings.setCustomMode(customMode);
            Dependencies.saveSettings(context, settings);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        ObjectBox.init(this);
        Utils.setLanguage(this, Dependencies.getLanguageCode(this));
        Settings settings = Dependencies.getSettings(this);
        initSettings(this, settings);
    }
}
