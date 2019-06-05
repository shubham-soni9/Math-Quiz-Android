package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GameResult implements Parcelable {
    private ArrayList<Question> questions;

    public GameResult() {

    }

    private GameResult(Parcel in) {
        questions = in.createTypedArrayList(Question.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(questions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameResult> CREATOR = new Creator<GameResult>() {
        @Override
        public GameResult createFromParcel(Parcel in) {
            return new GameResult(in);
        }

        @Override
        public GameResult[] newArray(int size) {
            return new GameResult[size];
        }
    };

    public ArrayList<Question> getQuestionList() {
        return questions == null ? new ArrayList<Question>() : questions;
    }

    public void setQuestionList(ArrayList<Question> questions) {
        this.questions = questions;
    }

}
