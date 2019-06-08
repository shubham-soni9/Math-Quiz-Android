package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Settings implements Parcelable {
    private CustomMode            generalMode;

    public Settings() {

    }

    protected Settings(Parcel in) {
        generalMode = in.readParcelable(CustomMode.class.getClassLoader());
    }

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    public CustomMode getGeneralMode() {
        return generalMode;
    }

    public void setGeneralMode(CustomMode customMode) {
        this.generalMode = customMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(generalMode, flags);
    }
}
