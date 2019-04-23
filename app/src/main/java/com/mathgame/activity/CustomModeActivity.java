package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.mathgame.R;
import com.mathgame.structure.BaseActivity;

public class CustomModeActivity extends BaseActivity {
    private RecyclerView rvCustomModeList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_mode);
        init();
    }

    private void init() {
        rvCustomModeList=findViewById(R.id.rvCustomModeList);
    }
}
