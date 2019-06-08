package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Settings implements Parcelable {
    public Settings(){

    }

    private CustomMode customMode;

    protected Settings(Parcel in) {
        customMode = in.readParcelable(CustomMode.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(customMode, flags);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public CustomMode getCustomMode() {
        return customMode;
    }

    public void setCustomMode(CustomMode customMode) {
        this.customMode = customMode;
    }

}
