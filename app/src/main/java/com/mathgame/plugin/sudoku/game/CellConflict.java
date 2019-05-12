package com.mathgame.plugin.sudoku.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Chris on 08.11.2015.
 */
public class CellConflict implements Parcelable {

    public static final Parcelable.Creator<CellConflict> CREATOR
                                                            = new Parcelable.Creator<CellConflict>() {
        public CellConflict createFromParcel(Parcel in) {
            return new CellConflict(in);
        }

        public CellConflict[] newArray(int size) {
            return new CellConflict[size];
        }
    };
    /*
     * A conflict is created for every cell.
     */
    private             GameCell                         c1 = null;
    private             GameCell                         c2 = null;

    public CellConflict(GameCell first, GameCell second) {
        c1 = first;
        c2 = second;
    }

    /**
     * recreate object from parcel
     */
    private CellConflict(Parcel in) {
        c1 = in.readParcelable(GameCell.class.getClassLoader());
        c2 = in.readParcelable(GameCell.class.getClassLoader());
    }

    public int getRowCell1() {
        return c1.getRow();
    }

    public int getRowCell2() {
        return c2.getRow();
    }

    public int getColCell1() {
        return c1.getCol();
    }

    public int getColCell2() {
        return c2.getCol();
    }

    public GameCell getCell1() {
        return c1;
    }

    public GameCell getCell2() {
        return c2;
    }

    public boolean contains(GameCell c) {
        return c1.equals(c) || c2.equals(c);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CellConflict)) {
            return false;
        }
        return c1.equals(((CellConflict) other).c1) && c2.equals(((CellConflict) other).c2)
                || c1.equals(((CellConflict) other).c2) && c2.equals(((CellConflict) other).c1);
    }

    @NonNull
    @Override
    public String toString() {
        String sb = "[Conflict " +
                c1.toString() +
                " " +
                c2.toString() +
                "]";
        return sb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(c1, 0);
        dest.writeParcelable(c2, 0);
    }
}
