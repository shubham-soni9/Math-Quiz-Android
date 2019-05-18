package com.mathgame.appdata;

import android.content.Context;

import com.mathgame.database.ObjectBox;
import com.mathgame.model.CustomMode;
import com.mathgame.model.CustomMode_;
import com.mathgame.model.Tutorial;
import com.mathgame.model.UniversalPojo;
import com.mathgame.util.Utils;

import java.util.ArrayList;

import io.objectbox.Box;

public class GameSettings {
    public static CustomMode getAdditionGame() {
        return getCustomMode(Codes.SettingsIds.ADDITION, Constant.MathSign.ADDITION);
    }

    public static CustomMode getSubtraction() {
        return getCustomMode(Codes.SettingsIds.SUBTRACTION, Constant.MathSign.SUBTRACTION);
    }

    public static CustomMode getMultiplication() {
        return getCustomMode(Codes.SettingsIds.MULTIPLICATON, Constant.MathSign.MULTIPLICATION);
    }

    public static CustomMode getDivision() {
        return getCustomMode(Codes.SettingsIds.DIVISION, Constant.MathSign.DIVISION);
    }

    public static CustomMode getSquareRoot() {
        return getCustomMode(Codes.SettingsIds.SQUARE_ROOT, Constant.MathSign.SQUARE_ROOT);
    }

    public static CustomMode getPercentage() {
        return getCustomMode(Codes.SettingsIds.PERCENTAGE, Constant.MathSign.PERCENTAGE);
    }

    private static CustomMode getCustomMode(int key, String mathSign) {
        Box<CustomMode> userBox = ObjectBox.get().boxFor(CustomMode.class);
        CustomMode customMode = userBox.query().equal(CustomMode_.uniqueId, key).build().findUnique();
        if (customMode == null) {
            customMode = new CustomMode();
            customMode.setUniqueId(key);
            customMode.setMathOperations(mathSign);
            userBox.put(customMode);
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
}
