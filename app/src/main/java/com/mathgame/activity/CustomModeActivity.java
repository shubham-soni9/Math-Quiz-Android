package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mathgame.R;
import com.mathgame.structure.BaseActivity;

public class CustomModeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_mode);
    }
}
