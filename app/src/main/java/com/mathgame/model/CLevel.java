package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CLevel implements Parcelable {
    public static final Creator<CLevel> CREATOR = new Creator<CLevel>() {
        @Override
        public CLevel createFromParcel(Parcel in) {
            return new CLevel(in);
        }

        @Override
        public CLevel[] newArray(int size) {
            return new CLevel[size];
        }
    };
    private int    difficulty;
    private String question_sample;
    private int    time_per_question;

    private CLevel(Parcel in) {
        difficulty = in.readInt();
        question_sample = in.readString();
        time_per_question = in.readInt();
    }

    public int getTime_per_question() {
        return time_per_question;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(difficulty);
        dest.writeString(question_sample);
        dest.writeInt(time_per_question);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getQuestionSample() {
        return question_sample;
    }

    public int getDifficulty() {
        return difficulty;
    }

}
