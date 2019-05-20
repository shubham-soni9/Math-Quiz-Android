package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CLevel implements Parcelable {
    private String            tutorial;
    private ArrayList<CLevel> questions;
    private String            question_sample;

    protected CLevel(Parcel in) {
        tutorial = in.readString();
        question_sample = in.readString();
        questions = in.createTypedArrayList(CLevel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tutorial);
        dest.writeString(question_sample);
        dest.writeTypedList(questions);
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

    public String getTutorial() {
        return tutorial;
    }

    public ArrayList<CLevel> getQuestions() {
        return questions;
    }

    public String getQuestionSample() {
        return question_sample;
    }

}
