package com.mathgame.appdata;

import com.mathgame.model.CustomMode;

public class GameSettings {
    public static CustomMode getAdditionGame() {
        CustomMode customMode = new CustomMode();
        customMode.setMathOperations("+");
        return customMode;
    }

    public static CustomMode getSubtraction() {
        CustomMode customMode = new CustomMode();
        customMode.setMathOperations("-");
        return customMode;
    }

    public static CustomMode getMultiplication() {
        CustomMode customMode = new CustomMode();
        customMode.setMathOperations("*");
        return customMode;
    }

    public static CustomMode getDivision() {
        CustomMode customMode = new CustomMode();
        customMode.setMathOperations("/");
        return customMode;
    }

    public static CustomMode getSquareRoot() {
        CustomMode customMode = new CustomMode();
        customMode.setMathOperations("sqt()");
        return customMode;
    }

    public static CustomMode getPercentage() {
        CustomMode customMode = new CustomMode();
        customMode.setMathOperations("%");
        return customMode;
    }
}
