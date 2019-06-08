package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mathgame.util.Utils;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class CustomMode implements Parcelable {
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

    @Id
    private long   id;
    private String title;
    private int    numberOfQuestions;
    private int    gameType;
    private String mathOperations;
    private int    timerValue;
    private int    skipNumbers;
    private int    difficulty;
    private String questionSample;

    public CustomMode() {

    }

    protected CustomMode(Parcel in) {
        id = in.readLong();
        title = in.readString();
        numberOfQuestions = in.readInt();
        gameType = in.readInt();
        mathOperations = in.readString();
        timerValue = in.readInt();
        skipNumbers = in.readInt();
        difficulty = in.readInt();
        questionSample = in.readString();
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
        dest.writeInt(gameType);
        dest.writeString(mathOperations);
        dest.writeInt(timerValue);
        dest.writeInt(skipNumbers);
        dest.writeInt(difficulty);
        dest.writeString(questionSample);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public String getMathOperations() {
        return mathOperations;
    }

    public void setMathOperations(String mathOperations) {
        this.mathOperations = mathOperations;
    }

    public int getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }

    public int getSkipNumbers() {
        return skipNumbers;
    }

    public void setSkipNumbers(int skipNumbers) {
        this.skipNumbers = skipNumbers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestionSample() {
        return Utils.assign(questionSample);
    }

    public void setQuestionSample(String questionSample) {
        this.questionSample = questionSample;
    }
}
