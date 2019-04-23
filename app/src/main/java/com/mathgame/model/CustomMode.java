package com.mathgame.model;

public class CustomMode  {

    private String title;
    private int numberOfQuestions;
    private int playerType;
    private int gameType;
    private String[] mathOperations;
    private int numberOfVariables;
    private int TimerType;
    private int timerValue;
    private int skipNumbers;

    public CustomMode(){

    }

    public CustomMode(String title, int numberOfQuestions, int playerType, int gameType, String[] mathOperations, int numberOfVariables, int timerType, int timerValue, int skipNumbers) {
        this.title = title;
        this.numberOfQuestions = numberOfQuestions;
        this.playerType = playerType;
        this.gameType = gameType;
        this.mathOperations = mathOperations;
        this.numberOfVariables = numberOfVariables;
        TimerType = timerType;
        this.timerValue = timerValue;
        this.skipNumbers = skipNumbers;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public int getPlayerType() {
        return playerType;
    }

    public int getGameType() {
        return gameType;
    }

    public String[] getMathOperations() {
        return mathOperations;
    }

    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    public int getTimerType() {
        return TimerType;
    }

    public int getTimerValue() {
        return timerValue;
    }

    public int getSkipNumbers() {
        return skipNumbers;
    }

}
