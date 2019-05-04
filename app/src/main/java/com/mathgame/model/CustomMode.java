package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class CustomMode implements Parcelable {
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
    private int difficulty;

    public CustomMode() {

    }

    protected CustomMode(Parcel in) {
        id = in.readLong();
        title = in.readString();
        numberOfQuestions = in.readInt();
        playerType = in.readInt();
        gameType = in.readInt();
        mathOperations = in.readString();
        numberOfVariables = in.readInt();
        timerType = in.readInt();
        timerValue = in.readInt();
        skipNumbers = in.readInt();
        difficulty = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeInt(numberOfQuestions);
        dest.writeInt(playerType);
        dest.writeInt(gameType);
        dest.writeString(mathOperations);
        dest.writeInt(numberOfVariables);
        dest.writeInt(timerType);
        dest.writeInt(timerValue);
        dest.writeInt(skipNumbers);
        dest.writeInt(difficulty);
    }

    public static final Creator<CustomMode> CREATOR = new Creator<CustomMode>() {
        @Override
        public CustomMode createFromParcel(Parcel in) {
            return new CustomMode(in);
        }

        @Override
        public CustomMode[] newArray(int size) {
            return new CustomMode[size];
        }
    };

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

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }


}
