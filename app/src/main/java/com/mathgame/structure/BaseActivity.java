package com.mathgame.structure;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.mathgame.R;
import com.mathgame.appdata.Dependencies;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    public static final int NAVDRAWER_LAUNCH_DELAY        = 250;
    public static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
    public static final int MAIN_CONTENT_FADEIN_DURATION  = 250;

    public Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getPointerCount() > 1) event.setAction(MotionEvent.ACTION_CANCEL);
        return super.onTouchEvent(event);
    }

    @Override
    public void onResume() {
        super.onResume();

        View mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        View mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
        }
    }

    protected Locale locale() {
        return Dependencies.getLocale();
    }
}
