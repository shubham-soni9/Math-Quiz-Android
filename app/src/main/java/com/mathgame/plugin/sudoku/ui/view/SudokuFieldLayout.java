package com.mathgame.plugin.sudoku.ui.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.mathgame.plugin.sudoku.controller.GameController;
import com.mathgame.plugin.sudoku.controller.Symbol;
import com.mathgame.plugin.sudoku.game.CellConflict;
import com.mathgame.plugin.sudoku.game.GameCell;
import com.mathgame.plugin.sudoku.game.ICellAction;
import com.mathgame.plugin.sudoku.game.listener.IHighlightChangedListener;

import java.util.LinkedList;

/**
 * Created by Timm Lippert on 11.11.2015.
 */
public class SudokuFieldLayout extends RelativeLayout implements IHighlightChangedListener {

    private final AttributeSet       attrs;
    private       SudokuCellView[][] gamecells;
    private       GameController     gameController;
    private final OnTouchListener    listener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v instanceof SudokuCellView) {

                SudokuCellView scv = ((SudokuCellView) v);

                int row = scv.getRow();
                int col = scv.getCol();

                gameController.selectCell(row, col);
            }
            return false;
        }
    };
    private       int                sectionHeight;
    private       int                sectionWidth;
    private       int                gameCellWidth;
    private       int                gameCellHeight;
    private       SharedPreferences  settings;
    private       Paint              p        = new Paint();
    private       boolean            isWidthLimiting;

    public SudokuFieldLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        setWillNotDraw(false);
        setBackgroundColor(Color.argb(255, 200, 200, 200));
    }

    public void setSettingsAndGame(SharedPreferences sharedPref, GameController gc) {

        if (sharedPref == null)
            throw new IllegalArgumentException("SharedPreferences may not be null.");
        if (gc == null) throw new IllegalArgumentException("GameController may not be null.");

        settings = sharedPref;
        gameController = gc;
        gameController.registerHighlightChangedListener(this);

        gamecells = new SudokuCellView[gc.getSize()][gc.getSize()];

        sectionHeight = gameController.getSectionHeight();
        sectionWidth = gameController.getSectionWidth();

        for (int i = 0; i < gameController.getSize(); i++) {
            for (int j = 0; j < gameController.getSize(); j++) {
                gamecells[i][j] = new SudokuCellView(getContext(), attrs);
                gamecells[i][j].setOnTouchListener(listener);
                addView(gamecells[i][j]);
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        for (int i = 0; i < gameController.getSize(); i++) {
            for (int j = 0; j < gameController.getSize(); j++) {
                gamecells[i][j].setEnabled(false);
                gamecells[i][j].setOnTouchListener(null);
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (gameController == null) return;

        p.setColor(Color.BLACK);
        p.setStrokeWidth(5);

        int horizontalSections = gameController.getSize() / sectionWidth;
        for (int i = 0; i <= horizontalSections; i++) {
            int offset = 0;
            if (!isWidthLimiting) {
                offset = (-2 + Math.round(i * 4f / (float) horizontalSections)) * -1;
            }
            int w = (i * getWidth() / horizontalSections) + offset;
            canvas.drawLine(w, 0, w, getHeight(), p);
        }

        int verticalSections = (gameController.getSize() / sectionHeight);
        for (int i = 0; i <= verticalSections; i++) {
            int offset = 0;
            if (isWidthLimiting) {
                offset = (-2 + Math.round(i * 4f / (float) verticalSections)) * -1;
            }
            int h = (i * getHeight() / verticalSections) + offset;
            canvas.drawLine(0, h, getWidth(), h, p);
        }
    }

    public void setSymbols(Symbol s) {
        for (SudokuCellView[] gamecell : gamecells) {
            for (int j = 0; j < gamecell.length; j++) {
                gamecell[j].setSymbols(s);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        isWidthLimiting = r - l == Math.min(r - l, b - t);

        if (changed && gameController != null) {
            gameCellWidth = (Math.min(r - l, b - t)) / gameController.getSize();
            gameCellHeight = (Math.min(r - l, b - t)) / gameController.getSize();

            for (int i = 0; i < gameController.getSize(); i++) {
                for (int j = 0; j < gameController.getSize(); j++) {
                    gamecells[i][j].setValues(gameCellWidth, gameCellHeight, sectionHeight, sectionWidth, gameController.getGameCell(i, j), gameController.getSize());
                }
            }
        }
    }

    @Override
    public void onHighlightChanged() {

        final int row = gameController.getSelectedRow();
        final int col = gameController.getSelectedCol();

        // Reset everything
        for (int i = 0; i < gameController.getSize(); i++) {
            for (int j = 0; j < gameController.getSize(); j++) {
                gamecells[i][j].setHighlightType(CellHighlightTypes.Default);
            }
        }

        // Set connected Fields
        if (gameController.isValidCellSelected()) {
            //String syncConnPref = sharedPref.getString(SudokuSettingsActivity., "");
            final boolean highlightConnected = settings.getBoolean("pref_highlight_connected", true);

            if (highlightConnected) {
                for (GameCell c : gameController.getConnectedCells(row, col)) {
                    gamecells[c.getRow()][c.getCol()].setHighlightType(CellHighlightTypes.Connected);
                }
            }
        }

        // highlight values
        final boolean highlightValues = settings.getBoolean("pref_highlight_vals", true);
        final boolean highlightNotes = settings.getBoolean("pref_highlight_notes", true);

        if (gameController.isValueHighlighted()) {
            for (GameCell c : gameController.actionOnCells(new ICellAction<LinkedList<GameCell>>() {
                @Override
                public LinkedList<GameCell> action(GameCell gc, LinkedList<GameCell> existing) {
                    if ((gameController.getHighlightedValue() == gc.getValue() && highlightValues)
                            || (gc.getNotes()[gameController.getHighlightedValue() - 1] && highlightNotes)) {
                        existing.add(gc);
                    }
                    return existing;
                }
            }, new LinkedList<GameCell>())) {
                gamecells[c.getRow()][c.getCol()].setHighlightType(CellHighlightTypes.Value_Highlighted);
            }
        }

        // Highlight selected/ current cell either green or red
        if (row != -1 && col != -1) {
            GameCell gc = gameController.getGameCell(row, col);
            if (gc.isFixed()) {
                gamecells[gc.getRow()][gc.getCol()].setHighlightType(CellHighlightTypes.Error);
            } else {
                gamecells[gc.getRow()][gc.getCol()].setHighlightType(CellHighlightTypes.Selected);
            }
        }

        // invalidate everything, so it gets redrawn
        for (int i = 0; i < gameController.getSize(); i++) {
            for (int j = 0; j < gameController.getSize(); j++) {
                gamecells[i][j].invalidate();
            }
        }
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);


        if (gameController == null) {
            return;
        }

        // draw error list
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);
        p.setColor(Color.RED);

        float offsetX = 0;
        float offsetY = 0;
        int row;
        int col;
        int row2;
        int col2;

        float radius = gameCellWidth / 2 - gameCellWidth / 16;

        for (CellConflict conflict : gameController.getErrorList()) {

            // draw the circles around the numbers
            row = conflict.getRowCell1();
            col = conflict.getColCell1();
            canvas.drawCircle(gameCellWidth * col + gameCellWidth / 2, gameCellHeight * row + gameCellHeight / 2, radius, p);

            row2 = conflict.getRowCell2();
            col2 = conflict.getColCell2();
            canvas.drawCircle(gameCellWidth * col2 + gameCellWidth / 2, gameCellHeight * row2 + gameCellHeight / 2, radius, p);

            // draw the line between the circles
            // either offset is 0 or it is the radius pointing in the direction of the other cell
            offsetX = (col == col2) ? 0f : (col < col2) ? radius : -radius;
            offsetY = (row == row2) ? 0f : (row < row2) ? radius : -radius;

            if (col != col2 && row != row2) {
                double alpha = Math.atan(((float) Math.abs(col2 - col)) / ((float) Math.abs(row2 - row)));
                offsetX *= Math.sin(alpha);
                offsetY *= Math.cos(alpha);
            }

            canvas.drawLine(
                    (gameCellWidth * col + gameCellWidth / 2) + offsetX,
                    (gameCellHeight * row + gameCellHeight / 2) + offsetY,
                    (gameCellWidth * col2 + gameCellWidth / 2) - offsetX,
                    (gameCellHeight * row2 + gameCellHeight / 2) - offsetY, p);
        }
    }
}
