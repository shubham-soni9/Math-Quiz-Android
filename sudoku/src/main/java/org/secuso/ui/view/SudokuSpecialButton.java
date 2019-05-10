package org.secuso.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by TMZ_LToP on 07.12.2015.
 */
public class SudokuSpecialButton extends ImageButton {
    private int              value = -1;
    private SudokuButtonType type  = SudokuButtonType.Unspecified;

    public SudokuSpecialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public SudokuButtonType getType() {
        return type;
    }

    public void setType(SudokuButtonType type) {
        this.type = type;
    }

}
