package com.mathgame.appdata;

import android.content.Context;

import com.mathgame.model.CLevel;
import com.mathgame.model.CustomMode;
import com.mathgame.model.Settings;
import com.mathgame.model.Tutorial;
import com.mathgame.model.UniversalPojo;
import com.mathgame.util.Utils;

import java.util.ArrayList;

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
        CustomMode customMode = null;
        Settings settings = Dependencies.getSettings(context);
        ArrayList<CustomMode> generalModeList = settings.getSpecificModeList();
        if (Utils.hasData(generalModeList)) {
            for (int i = 0; i < generalModeList.size(); i++) {
                if (generalModeList.get(i).getOperationId() == key) {
                    customMode = generalModeList.get(i);
                    break;
                }
            }
        }
        if (customMode == null) {
            customMode = Dependencies.getSettings(context).getGeneralMode();
            customMode.setMathOperations(mathSign);
            customMode.setOperationId(key);
            customMode.setUniqueId(Utils.getUniqueId());
        }
        return customMode;
    }

    public static void saveCustomMode(Context context, CustomMode mMode) {
        Settings settings = Dependencies.getSettings(context);
        ArrayList<CustomMode> generalModeList = settings.getSpecificModeList();
        if (Utils.hasData(generalModeList)) {
            for (int i = 0; i < generalModeList.size(); i++) {
                CustomMode customMode = generalModeList.get(i);
                if (customMode.getUniqueId().equalsIgnoreCase(mMode.getUniqueId())) {
                    generalModeList.set(i, mMode);
                    break;
                }
            }
        } else {
            generalModeList = new ArrayList<>();
            mMode.setUniqueId(Utils.getUniqueId());
            generalModeList.add(mMode);
        }
        settings.setSpecificModeList(generalModeList);
        Dependencies.saveSettings(context, settings);
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
