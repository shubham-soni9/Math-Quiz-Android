package com.mathgame.util;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class ViewUtils {
    public static void setBackgroundResource(@DrawableRes int resId, View... views) {
        for (View view : views) view.setBackgroundResource(resId);
    }

    public static void setBackgroundColor(Context context, @ColorRes int colorRes, View... views) {
        for (View view : views) view.setBackgroundColor(ContextCompat.getColor(context, colorRes));
    }

    public static void setCardBackgroundColor(Context context, @ColorRes int colorRes, CardView... views) {
        for (CardView view : views)
            view.setCardBackgroundColor(ContextCompat.getColor(context, colorRes));
    }

    public static void setVisibility(int visibility, View... views) {
        for (View view : views)
            view.setVisibility(visibility);
    }

    public static void setText(@StringRes int value, TextView... views) {
        for (TextView textView : views) {
            textView.setText(value);
        }
    }

    public static void setText(String value, TextView... views) {
        for (TextView textView : views) {
            textView.setText(value);
        }
    }
}
