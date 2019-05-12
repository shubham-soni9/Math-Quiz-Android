package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {
    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
    private int total;
    private int correct;
    private int incorrect;
    private int unAttempted;

    private Result(Parcel in) {
        total = in.readInt();
        correct = in.readInt();
        incorrect = in.readInt();
        unAttempted = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total);
        dest.writeInt(correct);
        dest.writeInt(incorrect);
        dest.writeInt(unAttempted);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getUnAttempted() {
        return unAttempted;
    }

    public void setUnAttempted(int unAttempted) {
        this.unAttempted = unAttempted;
    }

}