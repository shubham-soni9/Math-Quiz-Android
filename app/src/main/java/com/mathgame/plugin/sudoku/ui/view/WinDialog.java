package com.mathgame.plugin.sudoku.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mathgame.R;


/**
 * Created by TMZ_LToP on 30.01.2016.
 */
public class WinDialog extends Dialog {

    private String  timeString    = "";
    private String  hintString    = "";
    private boolean isNewBestTime = false;

    public WinDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public WinDialog(Context context, int themeResId, String timeString, String hintString, boolean isNewBestTime) {
        super(context, themeResId);
        setParam(timeString, hintString, isNewBestTime);
    }


    private void setParam(String timeString, String hintString, boolean isNewBestTime) {
        this.timeString = timeString;
        this.hintString = hintString;
        this.isNewBestTime = isNewBestTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ((TextView) findViewById(R.id.win_hints)).setText(hintString);
        ((TextView) findViewById(R.id.win_time)).setText(timeString);
        if (isNewBestTime) {
            findViewById(R.id.win_new_besttime).setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public Bundle onSaveInstanceState() {
        Bundle bundle = super.onSaveInstanceState();
        bundle.putString("hintString", hintString);
        bundle.putString("timeString", timeString);
        bundle.putBoolean("isNewBestTime", isNewBestTime);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState == null) return;

        hintString = savedInstanceState.getString("hintString");
        timeString = savedInstanceState.getString("timeString");
        isNewBestTime = savedInstanceState.getBoolean("isNewBestTime");
    }
}
