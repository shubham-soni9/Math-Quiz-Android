package com.mathgame.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mathgame.R;
import com.mathgame.appdata.Codes;
import com.rey.material.widget.CheckBox;

import java.util.Locale;

public class Utils {
    private static final String TAG = Utils.class.getName();

    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        for (View view : views)
            view.setOnClickListener(listener);
    }

    public static void setOnCheckChangedListener(CompoundButton.OnCheckedChangeListener listener, CompoundButton... views) {
        for (CompoundButton view : views)
            view.setOnCheckedChangeListener(listener);
    }

    public static String get(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String get(TextView editText) {
        return editText.getText().toString().trim();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isEmpty(EditText editText) {
        return Utils.isEmpty(Utils.get(editText));
    }

    public static void snackBar(Activity activity, String message, int type) {
        try {
            snackBar(activity, message, activity.getWindow().getDecorView().findViewById(android.R.id.content), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void snackBar(Activity activity, int messageId, int type) {
        snackBar(activity, activity.getString(messageId), type);
    }

    public static void snackBar(Activity activity, String message) {
        try {
            snackBar(activity, message, Codes.SnackBarType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void snackBar(Activity activity, int messageId) {
        try {
            snackBar(activity, activity.getString(messageId), Codes.SnackBarType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void snackBar(final Activity activity, final String message, final View view, final int type) {

        if (activity == null) return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setDuration(2500);
                    View view = snackbar.getView();
                    TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setMaxLines(3);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                    tv.setTextAppearance(activity, R.style.CustomTextAppearance_Light);
                    tv.setTextColor(ContextCompat.getColor(activity, R.color.white));
                    view.setBackgroundColor(ContextCompat.getColor(activity, type == Codes.SnackBarType.SUCCESS ? R.color.snackbar_bg_color_success : R.color.snackbar_bg_color_error));
                    snackbar.show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean isAnyCheckboxChecked(com.rey.material.widget.CheckBox... checkBoxes) {
        for (CheckBox checkbox : checkBoxes) {
            if (checkbox.isChecked()) return true;
        }
        return false;
    }

    public static String convertToTwoDigit(int value) {
        return new TwoDigitFormatter().format(value);
    }

    public static void logRequestBody(Object object) {
        try {
            Log.e(TAG, new Gson().toJson(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(500);
            }
        }
    }

    public static String toDecimal(final double decimalInput) {
        return toDecimal(decimalInput, Locale.US, 2);
    }

    private static String toDecimal(final double decimalInput, final Locale locale,
                                             final int decimalPlace) {
        String mString = "%." + decimalPlace + "f";
        return String.format(locale, mString, decimalInput);
    }
    public static double toDouble(String convertible) {

        double converted;

        try {
            converted = Double.parseDouble(convertible);
        } catch (Exception ex) {
            converted = 0.0;
            ex.printStackTrace();
        }

        return converted;
    }

}
