package org.secuso.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import org.secuso.controller.Symbol;
import org.secuso.game.GameCell;

/**
 * Created by TMZ_LToP on 10.11.2015.
 */
public class SudokuCellView extends View {

    GameCell                    mGameCell;
    int                         mWidth;
    int                         mHeight;
    int                         mSectionHeight;
    int                         mSectionWidth;
    int                         mRow;
    int                         mCol;
    int                         size;
    boolean                     selected;
    CellHighlightTypes          highlightType = CellHighlightTypes.Default;
    Symbol                      symbolsToUse  = Symbol.Default;
    RelativeLayout.LayoutParams params;


    public SudokuCellView(Context context) {
        super(context);
    }

    public SudokuCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSelected(boolean b) {
        this.selected = b;
    }

    public void setValues(int width, int height, int sectionHeight, int sectionWidth, GameCell gameCell, int size) {
        mSectionHeight = sectionHeight;
        mSectionWidth = sectionWidth;
        mGameCell = gameCell;
        mWidth = width;
        mHeight = height;
        mRow = gameCell.getRow();
        mCol = gameCell.getCol();
        this.size = size;
    }

    public void setSymbols(Symbol s) {
        symbolsToUse = s;
    }

    public void setHighlightType(CellHighlightTypes highlightType) {
        this.highlightType = highlightType;
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(mGameCell == null) return false;

        if(motionEvent.getAction() == motionEvent.ACTION_DOWN) {
            highlightType = CellHighlightTypes.Selected;
        }

        return true;
    }*/

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (this.params == null) {
            params = new RelativeLayout.LayoutParams(mWidth, mHeight);
        }

        // Set Layout
        params.width = mWidth;
        params.height = mHeight;
        params.topMargin = mRow * mHeight;
        params.leftMargin = mCol * mWidth;
        this.setLayoutParams(params);

        // Draw single Field
        drawInfo(canvas);
    }

    private void drawInfo(Canvas canvas) {
        Paint p = new Paint();
        switch (highlightType) {
            case Default:
                p.setColor(Color.WHITE);
                break;
            case Error:
                p.setColor(Color.LTGRAY);
                break;
            case Selected:
                p.setColor(Color.GREEN);
                break;
            case Connected:
                p.setColor(Color.WHITE);
                drawBackground(canvas, 3, 3, mWidth - 3, mHeight - 3, p);
                p.setColor(Color.YELLOW);
                p.setAlpha(100);
                break;
            case Value_Highlighted:
                p.setColor(Color.YELLOW);
                break;
            case Value_Highlighted_Selected:
                p.setColor(Color.CYAN);
            default:
                p.setColor(Color.WHITE);
        }


        drawBackground(canvas, 3, 3, mWidth - 3, mHeight - 3, p);

        // if there is no mGameCell .. we can not retrieve the information to draw
        if (mGameCell != null) {
            drawValue(canvas);
        }
    }

    public void drawBackground(Canvas canvas, int left, int top, int right, int bottom, Paint p) {
        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawRect(rect, p);
    }

    public void drawValue(Canvas canvas) {
        Paint p = new Paint();
        int root = (int) Math.sqrt(size);
        int j = root + 1;
        int k = root;
        if (mGameCell.getValue() == 0) {
            for (int i = 0; i < mGameCell.getNotes().length; i++) {
                if (mGameCell.getNotes()[i]) {
                    p.setTypeface(Typeface.SANS_SERIF);
                    p.setTextSize(mWidth / 4);
                    p.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(Symbol.getSymbol(symbolsToUse, i), (mWidth * 1 / (size + root)) * k, (mWidth * 1 / (size + root + 1)) * j, p);
                    /*canvas.drawText(String.valueOf(1), (mWidth * 1 / 12)*3, (mWidth* 1 / 12)*3, p);
                    canvas.drawText(String.valueOf(2),(mWidth*1/12)*7, (mWidth* 1 / 12)*7,p );
                    canvas.drawText(String.valueOf(3),(mWidth*1/12)*11, (mWidth* 1 / 12)*11,p );*/
                }
                k += root + 1;
                if (k > (size + root)) {
                    k = root;
                    j += root + 1;
                }
            }
            return;
        }


        if (mGameCell.isFixed()) {
            p.setTypeface(Typeface.DEFAULT_BOLD);
        }
        p.setAntiAlias(true);
        p.setTextSize(Math.min(mHeight * 3 / 4, mHeight * 3 / 4));
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Symbol.getSymbol(symbolsToUse, mGameCell.getValue() - 1), mHeight / 2, mHeight / 2 + mHeight / 4, p);
    }

    public int getRow() {
        return mRow;
    }

    public int getCol() {
        return mCol;
    }

    /*@Override
    public Parcelable onSaveInstanceState() {
        Parcelable state = super.onSaveInstanceState();

        return state;
    }*/
}
