package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mathgame.R;
import com.mathgame.structure.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.settings);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_settings;
    }
}
