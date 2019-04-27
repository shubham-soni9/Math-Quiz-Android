package com.mathgame.appdata;

import com.mathgame.model.CustomMode;

public class GameSettings {

    public static CustomMode getAdditionGame() {
        CustomMode customMode = new CustomMode();
        customMode.setTitle("Addition");
        customMode.setGameType(Codes.GameType.MULTIPLE_CHOICE.value);
        customMode.setTimerType(Codes.TimerType.NONE.value);
        customMode.setPlayerType(Codes.PlayerType.SINGLE.value);
        customMode.setNumberOfVariables(2);
        customMode.setNumberOfQuestions(10);
        customMode.setSkipNumbers(0);
        customMode.setMathOperations("+");
        customMode.setTimerValue(0);
        return customMode;
    }
}
