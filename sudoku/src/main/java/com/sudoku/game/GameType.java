package com.sudoku.game;

import android.os.Parcel;
import android.os.Parcelable;


import com.sudoku.R;

import java.util.LinkedList;

/**
 * Created by Chris on 09.11.2015.
 */
public enum GameType implements Parcelable {
    Unspecified(1, 1, 1, R.string.gametype_unspecified, R.drawable.icon_default_6x6),
    Default_9x9(9, 3, 3, R.string.gametype_default_9x9, R.drawable.icon_default_9x9),
    Default_12x12(12, 3, 4, R.string.gametype_default_12x12, R.drawable.icon_default_12x12),
    Default_6x6(6, 2, 3, R.string.gametype_default_6x6, R.drawable.icon_default_6x6),
    X_9x9(9, 3, 3, R.string.gametype_x_9x9, R.drawable.icon_default_9x9),
    Hyper_9x9(9, 3, 3, R.string.gametype_hyper_9x9, R.drawable.icon_default_9x9);

    public static final Parcelable.Creator<GameType> CREATOR = new Parcelable.Creator<GameType>() {
        public GameType createFromParcel(Parcel in) {
            GameType g = GameType.values()[in.readInt()];
            g.resIDString = in.readInt();
            g.sectionWidth = in.readInt();
            g.sectionHeight = in.readInt();
            g.size = in.readInt();
            g.resIDImage = in.readInt();
            return g;
        }

        public GameType[] newArray(int size) {
            return new GameType[size];
        }
    };
    // change pictures for unsepc x9x9 and hyper 9x9 as soon as available
    int resIDString;
    int sectionWidth;
    int sectionHeight;
    int size;
    int resIDImage;

    GameType(int size, int sectionHeight, int sectionWidth, int resIDString, int resIDImage) {
        this.resIDImage = resIDImage;
        this.size = size;
        this.sectionHeight = sectionHeight;
        this.sectionWidth = sectionWidth;
        this.resIDString = resIDString;
    }

    public static LinkedList<GameType> getValidGameTypes() {
        LinkedList<GameType> result = new LinkedList<>();
        result.add(Default_6x6);
        result.add(Default_9x9);
        result.add(Default_12x12);
        return result;
    }

    public int getResIDImage() {
        return resIDImage;
    }

    public int getSectionHeight() {
        return sectionHeight;
    }

    public int getSize() {
        return size;
    }

    public int getSectionWidth() {
        return sectionWidth;
    }

    public int getStringResID() {
        return resIDString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
        dest.writeInt(resIDString);
        dest.writeInt(sectionWidth);
        dest.writeInt(sectionHeight);
        dest.writeInt(size);
        dest.writeInt(resIDImage);
    }
}
