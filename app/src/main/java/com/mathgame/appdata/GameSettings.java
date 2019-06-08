package com.mathgame.appdata;

import android.content.Context;

import com.mathgame.database.ObjectBox;
import com.mathgame.model.CLevel;
import com.mathgame.model.CustomMode;
import com.mathgame.model.CustomMode_;
import com.mathgame.model.Tutorial;
import com.mathgame.model.UniversalPojo;
import com.mathgame.util.Utils;

import java.util.ArrayList;

import io.objectbox.Box;

public class GameSettings {
    public static CustomMode getAdditionGame(Context context) {
        return getCustomMode(context, Codes.SettingsIds.ADDITION, Constant.MathSign.ADDITION);
    }

    public static CustomMode getSubtraction(Context context) {
        return getCustomMode(context, Codes.SettingsIds.SUBTRACTION, Constant.MathSign.SUBTRACTION);
    }

    public static CustomMode getMultiplication(Context context) {
        return getCustomMode(context, Codes.SettingsIds.MULTIPLICATON, Constant.MathSign.MULTIPLICATION);
    }

    public static CustomMode getDivision(Context context) {
        return getCustomMode(context, Codes.SettingsIds.DIVISION, Constant.MathSign.DIVISION);
    }

    public static CustomMode getSquareRoot(Context context) {
        return getCustomMode(context, Codes.SettingsIds.SQUARE_ROOT, Constant.MathSign.SQUARE_ROOT);
    }

    public static CustomMode getPercentage(Context context) {
        return getCustomMode(context, Codes.SettingsIds.PERCENTAGE, Constant.MathSign.PERCENTAGE);
    }

    private static CustomMode getCustomMode(Context context, int key, String mathSign) {
        Box<CustomMode> userBox = ObjectBox.get().boxFor(CustomMode.class);
        CustomMode customMode = userBox.query().equal(CustomMode_.id, key).build().findUnique();
        if (customMode == null) {
            customMode = Dependencies.getSettings(context).getCustomMode();
            customMode.setMathOperations(mathSign);
        }
        return customMode;
    }

    public static void saveCustomMode(CustomMode customMode) {
        Box<CustomMode> userBox = ObjectBox.get().boxFor(CustomMode.class);
        userBox.put(customMode);
    }

    public static ArrayList<Tutorial> getTutorialList(Context context) {
        String json = Utils.loadJSONFromAsset(context, Constant.JSONFileNames.TUTORIAL_FILE);
        UniversalPojo universalPojo = Utils.toResponseModel(json, UniversalPojo.class);
        return universalPojo.getTutorials();
    }

    public static ArrayList<CLevel> getLevelList(Context context) {
        String json = Utils.loadJSONFromAsset(context, Constant.JSONFileNames.CAREER_QUESTION);
        UniversalPojo universalPojo = Utils.toResponseModel(json, UniversalPojo.class);
        return universalPojo.getLevelList();
    }
}
