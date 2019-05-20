package com.mathgame.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UniversalPojo implements Parcelable {
    private ArrayList<Tutorial>  tutorials;
    private ArrayList<CLevel> levels;


    protected UniversalPojo(Parcel in) {
        tutorials = in.createTypedArrayList(Tutorial.CREATOR);
        levels = in.createTypedArrayList(CLevel.CREATOR);
    }

    public static final Creator<UniversalPojo> CREATOR = new Creator<UniversalPojo>() {
        @Override
        public UniversalPojo createFromParcel(Parcel in) {
            return new UniversalPojo(in);
        }

        @Override
        public UniversalPojo[] newArray(int size) {
            return new UniversalPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<CLevel> getLevelList() {
        return levels;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tutorials);
        dest.writeTypedList(levels);
    }

    public ArrayList<Tutorial> getTutorials() {
        return tutorials;
    }
}
