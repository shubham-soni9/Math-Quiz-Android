package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mathgame.R;
import com.mathgame.adapter.CustomModeAdapter;
import com.mathgame.model.CustomMode;
import com.mathgame.structure.BaseActivity;

import java.util.ArrayList;

public class CustomModeActivity extends BaseActivity {
    private RecyclerView rvCustomModeList;
    private CustomModeAdapter adapter;
    private ArrayList<CustomMode> customModes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_mode);
        init();
        setList();
    }

    private void setList() {
        adapter=new CustomModeAdapter(customModes);
        rvCustomModeList.setAdapter(adapter);
    }

    private void init() {
        rvCustomModeList=findViewById(R.id.rvCustomModeList);
        rvCustomModeList.setLayoutManager(new LinearLayoutManager(this));

    }
}
