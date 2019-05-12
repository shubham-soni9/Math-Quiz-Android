package com.mathgame.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mathgame.R;
import com.mathgame.appdata.Codes;
import com.mathgame.appdata.Constant;
import com.rey.material.widget.CheckBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isEmpty(EditText editText) {
        return Utils.isEmpty(Utils.get(editText));
    }

    private static void snackBar(Activity activity, String message, int type) {
        try {
            snackBar(activity, message, activity.getWindow().getDecorView().findViewById(android.R.id.content), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String assign(String assignable, String alternative) {

        return assignable == null || assignable.isEmpty() ?
                (alternative == null ? Constant.EMPTY : alternative) :
                (assignable.equals("null") ? assign(alternative) : assignable);
    }

    private static String assign(String assignable) {

        return assignable == null || assignable.equalsIgnoreCase("[]") || assignable.equals("null") ?
                Constant.EMPTY : assignable;
    }

    public static void snackBar(Activity activity, int messageId, int type) {
        snackBar(activity, activity.getString(messageId), type);
    }

    private static void snackBar(Activity activity, String message) {
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

    private static void snackBar(final Activity activity, final String message, final View view, final int type) {

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
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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

    private static double round(double value) {
        if (2 < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String convertToTwoDigit(int value) {
        return new TwoDigitFormatter().format(value);
    }

    public static double twoDecimal(final double decimalInput) {
        return round(decimalInput);
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
        return toDecimal(decimalInput, Locale.US);
    }

    private static String toDecimal(final double decimalInput, final Locale locale) {
        String mString = "%." + 2 + "f";
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

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    private static void hideSoftKeyboard(Activity activity) {

        View focusedView = activity.getCurrentFocus();
        if (focusedView == null) return;

        IBinder windowToken = focusedView.getWindowToken();
        if (windowToken == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
    }

    /**
     * Method used to hide keyboard if outside touched.
     *
     */
    public static void showSoftKeyboard(Activity activity) {

        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSoftKeyboard(Activity activity, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openEmailApp(Activity context, String subject, String message) {
        try {
            Utils.hideSoftKeyboard(context);
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String uriText = "mailto:" + Uri.encode("shubhamsonicse@gmail.com") +
                    "?subject=" + Uri.encode(subject) +
                    "&body=" + Uri.encode(message);
            emailIntent.setData(Uri.parse(uriText));
            context.startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
        } catch (Exception e) {
            Utils.snackBar(context, context.getString(R.string.some_error_occurred));
            e.printStackTrace();
        }
    }

    public static void openOtherApps(Context context){
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
