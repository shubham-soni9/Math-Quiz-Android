package com.sudoku.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;


import com.sudoku.R;

import java.util.LinkedList;

/**
 * Created by Chris on 18.11.2015.
 */
public enum GameDifficulty implements Parcelable {

    Unspecified(R.string.gametype_unspecified),
    Easy(R.string.difficulty_easy),
    Moderate(R.string.difficulty_moderate),
    Hard(R.string.difficulty_hard),
    Challenge(R.string.difficulty_challenge);

    public static final Parcelable.Creator<GameDifficulty> CREATOR
            = new Parcelable.Creator<GameDifficulty>() {
        public GameDifficulty createFromParcel(Parcel in) {
            GameDifficulty g = GameDifficulty.values()[in.readInt()];
            g.resID = in.readInt();
            return g;
        }

        public GameDifficulty[] newArray(int size) {
            return new GameDifficulty[size];
        }
    };
    private int resID;

    GameDifficulty(@StringRes int resID) {
        //getResources().getString(resID);
        this.resID = resID;
    }

    public static LinkedList<GameDifficulty> getValidDifficultyList() {
        LinkedList<GameDifficulty> validList = new LinkedList<>();
        validList.add(Easy);
        validList.add(Moderate);
        validList.add(Hard);
        validList.add(Challenge);
        return validList;
    }

    public int getStringResID() {
        return resID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
        dest.writeInt(resID);
    }

}
