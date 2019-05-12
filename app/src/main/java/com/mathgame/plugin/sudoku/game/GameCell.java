package com.mathgame.plugin.sudoku.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.mathgame.plugin.sudoku.game.listener.IModelChangedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris on 06.11.2015.
 */
public class GameCell implements Cloneable, Parcelable {

    public static final Parcelable.Creator<GameCell> CREATOR
                                                                           = new Parcelable.Creator<GameCell>() {
        public GameCell createFromParcel(Parcel in) {
            return new GameCell(in);
        }

        public GameCell[] newArray(int size) {
            return new GameCell[size];
        }
    };
    private             int                          row                   = 0;
    private             int                          col                   = 0;
    private             int                          value                 = 0;
    private             boolean                      fixed                 = false;
    private             int                          noteCount             = 0;
    private             boolean[]                    notes;
    private             int                          size                  = 0;
    private             List<IModelChangedListener>  modelChangedListeners = new LinkedList<>();

    public GameCell(int row, int col, int size) {
        this(row, col, size, 0);
    }

    /**
     * Constructor with init parameter. The cell will have that value and be uneditable.
     *
     * @param val the start value to be saved in the cell
     */
    public GameCell(int row, int col, int size, int val) {
        this.row = row;
        this.col = col;
        this.size = size;
        if (0 < val && val <= size) {
            setValue(val);
            setFixed(true);
        } else {
            setValue(0);
            setFixed(false);
        }
    }

    /**
     * recreate object from parcel
     */
    private GameCell(Parcel in) {
        row = in.readInt();
        col = in.readInt();
        value = in.readInt();
        size = in.readInt();
        fixed = in.readInt() == 1;
        noteCount = in.readInt();
        notes = new boolean[size];
        in.readBooleanArray(notes);

        removeAllListeners();
    }

    public int getValue() {
        return value;
    }

    /**
     * Set the value of the cell, only if cell isn't fixed.
     *
     * @param val the value to be assigned to the cell.
     */
    public void setValue(int val) {
        if (!isFixed()) {
            deleteNotes();
            value = val;
            notifyListeners();
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Toggle notes in this cell, if cell isn't fixed.
     *
     * @param val the value to be toggled.
     */
    public void toggleNote(int val) {
        if (!isFixed()) {
            noteCount = notes[val - 1] ? noteCount - 1 : noteCount + 1;
            notes[val - 1] = !notes[val - 1];
            notifyListeners();
        }
    }

    public void setNote(int val) {
        if (!isFixed()) {
            noteCount = notes[val - 1] ? noteCount : noteCount + 1;
            notes[val - 1] = true;
            notifyListeners();
        }
    }

    public void deleteNote(int val) {
        if (!isFixed()) {
            noteCount = notes[val - 1] ? noteCount - 1 : noteCount;
            notes[val - 1] = false;
            notifyListeners();
        }
    }

    public int getNoteCount() {
        return noteCount;
    }

    public boolean[] getNotes() {
        return notes;
    }

    /**
     * Clear the notes array (set everything to false).
     */
    private void deleteNotes() {
        noteCount = 0;
        notes = new boolean[size];
        notifyListeners();
    }

    public boolean isFixed() {
        return fixed;
    }

    private void setFixed(boolean b) {
        fixed = b;
    }

    public boolean hasValue() {
        return value > 0;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GameCell)) return false;
        if (((GameCell) other).getCol() != this.getCol()) return false;
        if (((GameCell) other).getRow() != this.getRow()) return false;
        if (((GameCell) other).isFixed() != this.isFixed()) return false;
        if (((GameCell) other).getValue() != this.getValue()) return false;
        if (((GameCell) other).getNotes().length != this.getNotes().length) return false;
        for (int i = 0; i < notes.length; i++) {
            if (((GameCell) other).getNotes()[i] != this.getNotes()[i]) return false;
        }
        return true;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        if (value == 0) {
            sb.append("{");
            boolean addedNotes = false;
            for (int i = 0; i < size; i++) {
                if (notes[i]) {
                    if (addedNotes) {
                        sb.append(" ,");
                    }
                    sb.append(i + 1);
                    addedNotes = true;
                }
            }
            sb.append("}");
        } else {
            sb.append(value);
        }

        sb.append(" ");
        sb.append("(");
        sb.append(row);
        sb.append("|");
        sb.append(col);
        sb.append(")");
        sb.append("]");

        return sb.toString();
    }

    public void reset() {
        if (isFixed()) {
            return;
        }
        setValue(0);
    }

    @Override
    public GameCell clone() throws CloneNotSupportedException {
        GameCell clone = (GameCell) super.clone();
        // keep listeners .. so we can just replace the board and still have the listeners
        clone.notes = (notes == null) ? null : Arrays.copyOf(notes, notes.length);

        return clone;
    }

    public void registerOnModelChangeListener(IModelChangedListener listener) {
        if (!modelChangedListeners.contains(listener)) {
            modelChangedListeners.add(listener);
        }
    }

    public void removeOnModelChangeListener(IModelChangedListener listener) {
        if (modelChangedListeners.contains(listener)) {
            modelChangedListeners.remove(listener);
        }
    }

    private void notifyListeners() {
        for (IModelChangedListener m : modelChangedListeners) {
            m.onModelChange();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(row);
        dest.writeInt(col);
        dest.writeInt(value);
        dest.writeInt(size);
        dest.writeInt(fixed ? 1 : 0);
        dest.writeInt(noteCount);
        dest.writeBooleanArray(notes);
    }

    public void removeAllListeners() {
        modelChangedListeners = new LinkedList<>();
    }
}
