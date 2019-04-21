package com.mathgame;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

public class AdditionActivity extends BaseActivity {
    private RoundedHorizontalProgressBar pbTimer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        pbTimer=findViewById(R.id.pbTimer);
        pbTimer.animateProgress(8000, 0, 100);

    }
}
