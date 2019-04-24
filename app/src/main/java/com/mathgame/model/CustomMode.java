package com.mathgame.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class CustomMode {
    @Id
    public long id;

    private String title;
    private int    numberOfQuestions;
    private int    playerType;
    private int    gameType;
    private String mathOperations;
    private int    numberOfVariables;
    private int    timerType;
    private int    timerValue;
    private int    skipNumbers;

    public CustomMode() {

    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public void setMathOperations(String mathOperations) {
        this.mathOperations = mathOperations;
    }

    public void setNumberOfVariables(int numberOfVariables) {
        this.numberOfVariables = numberOfVariables;
    }

    public void setTimerType(int timerType) {
        this.timerType = timerType;
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }

    public void setSkipNumbers(int skipNumbers) {
        this.skipNumbers = skipNumbers;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMathOperations() {
        return mathOperations;
    }

    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    public int getTimerType() {
        return timerType;
    }

    public int getTimerValue() {
        return timerValue;
    }

    public int getSkipNumbers() {
        return skipNumbers;
    }

}
