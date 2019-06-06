package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CLevel implements Parcelable {
    private int    difficulty;
    private String question_sample;

    private CLevel(Parcel in) {
        difficulty = in.readInt();
        question_sample = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(difficulty);
        dest.writeString(question_sample);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getQuestionSample() {
        return question_sample;
    }
    public int getDifficulty() {
        return difficulty;
    }

}
