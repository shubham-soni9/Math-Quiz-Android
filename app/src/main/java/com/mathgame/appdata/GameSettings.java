package com.mathgame.appdata;

import com.mathgame.model.CustomMode;

public class GameSettings {

    public static CustomMode getAdditionGame() {
        CustomMode customMode = new CustomMode();
        customMode.setTitle("Addition");
        customMode.setGameType(Codes.GameType.MULTIPLE_CHOICE.value);
        customMode.setTimerType(Codes.TimerType.PER_QUESTION.value);
        customMode.setPlayerType(Codes.PlayerType.SINGLE.value);
        customMode.setNumberOfVariables(2);
        customMode.setNumberOfQuestions(10);
        customMode.setSkipNumbers(0);
        customMode.setMathOperations("+");
        customMode.setTimerValue(10);
customMode.setDifficulty(Constant.DifficultyLevel.MEDIUM);
        return customMode;
    }

    public static CustomMode getSubtraction() {
        CustomMode customMode = new CustomMode();
        customMode.setTitle("Addition");
        customMode.setGameType(Codes.GameType.MULTIPLE_CHOICE.value);
        customMode.setTimerType(Codes.TimerType.NONE.value);
        customMode.setPlayerType(Codes.PlayerType.SINGLE.value);
        customMode.setNumberOfVariables(2);
        customMode.setNumberOfQuestions(10);
        customMode.setSkipNumbers(0);
        customMode.setMathOperations("-");
        customMode.setTimerValue(0);
        customMode.setDifficulty(Constant.DifficultyLevel.SMALL);
        return customMode;
    }

    public static CustomMode getMutltiplication() {
        CustomMode customMode = new CustomMode();
        customMode.setTitle("Addition");
        customMode.setGameType(Codes.GameType.MULTIPLE_CHOICE.value);
        customMode.setTimerType(Codes.TimerType.NONE.value);
        customMode.setPlayerType(Codes.PlayerType.SINGLE.value);
        customMode.setNumberOfVariables(2);
        customMode.setNumberOfQuestions(10);
        customMode.setSkipNumbers(0);
        customMode.setMathOperations("*");
        customMode.setTimerValue(0);
        customMode.setDifficulty(Constant.DifficultyLevel.SMALL);
        return customMode;
    }

    public static CustomMode getDivision() {
        CustomMode customMode = new CustomMode();
        customMode.setTitle("Addition");
        customMode.setGameType(Codes.GameType.MULTIPLE_CHOICE.value);
        customMode.setTimerType(Codes.TimerType.NONE.value);
        customMode.setPlayerType(Codes.PlayerType.SINGLE.value);
        customMode.setNumberOfVariables(2);
        customMode.setNumberOfQuestions(10);
        customMode.setSkipNumbers(0);
        customMode.setMathOperations("/");
        customMode.setTimerValue(0);
        customMode.setDifficulty(Constant.DifficultyLevel.SMALL);
        return customMode;
    }

    public static CustomMode getSquareRoot() {
        CustomMode customMode = new CustomMode();
        customMode.setTitle("Addition");
        customMode.setGameType(Codes.GameType.MULTIPLE_CHOICE.value);
        customMode.setTimerType(Codes.TimerType.NONE.value);
        customMode.setPlayerType(Codes.PlayerType.SINGLE.value);
        customMode.setNumberOfVariables(1);
        customMode.setNumberOfQuestions(10);
        customMode.setSkipNumbers(0);
        customMode.setMathOperations("sqt()");
        customMode.setTimerValue(0);
        customMode.setDifficulty(Constant.DifficultyLevel.SMALL);
        return customMode;
    }

    public static CustomMode getPercentage() {
        CustomMode customMode = new CustomMode();
        customMode.setTitle("Addition");
        customMode.setGameType(Codes.GameType.MULTIPLE_CHOICE.value);
        customMode.setTimerType(Codes.TimerType.NONE.value);
        customMode.setPlayerType(Codes.PlayerType.SINGLE.value);
        customMode.setNumberOfVariables(2);
        customMode.setNumberOfQuestions(10);
        customMode.setSkipNumbers(0);
        customMode.setMathOperations("%");
        customMode.setTimerValue(0);
        customMode.setDifficulty(Constant.DifficultyLevel.SMALL);
        return customMode;
    }
}
