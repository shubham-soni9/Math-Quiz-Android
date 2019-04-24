package com.mathgame.util;

import com.mathgame.plugin.numberpicker.NumberPicker;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TwoDigitFormatter implements NumberPicker.Formatter {
    final StringBuilder mBuilder = new StringBuilder();

    char mZeroDigit;
    java.util.Formatter mFmt;

    final Object[] mArgs = new Object[1];

    Locale mLocale;

    TwoDigitFormatter() {
        mLocale = Locale.getDefault();
        init(mLocale);
    }

    private void init(Locale locale) {
        mFmt = createFormatter(locale);
        mZeroDigit = getZeroDigit(locale);
    }

    public String format(int value) {
        Locale currentLocale = Locale.getDefault();
        // to force the locale value set by using setter method
        if (!mLocale.equals(currentLocale)) {
            currentLocale = mLocale;
        }
        if (mZeroDigit != getZeroDigit(currentLocale)) {
            init(currentLocale);
        }
        mArgs[0] = value;
        mBuilder.delete(0, mBuilder.length());
        mFmt.format("%02d", mArgs);
        return mFmt.toString();
    }

    private static char getZeroDigit(Locale locale) {
        // return LocaleData.get(locale).zeroDigit;
        return new DecimalFormatSymbols(locale).getZeroDigit();
    }

    // to force the locale value set by using setter method
    void setLocale(Locale locale) {
        if (this.mLocale != null && mLocale.equals(locale)) {
            return;
        }
        this.mLocale = locale;
        init(mLocale);
    }

    private java.util.Formatter createFormatter(Locale locale) {
        return new java.util.Formatter(mBuilder, locale);
    }
}
