package com.mathgame.util;

import android.view.View;

public class Utils {

    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        for (View view : views)
            view.setOnClickListener(listener);
    }

}
