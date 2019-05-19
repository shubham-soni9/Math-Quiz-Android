package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mathgame.R;
import com.mathgame.adapter.LevelAdapter;
import com.mathgame.appdata.GameSettings;
import com.mathgame.structure.BaseActivity;

import java.util.Objects;

public class CareerLevelActivity extends BaseActivity {
    private RecyclerView rvLevelList;
    private Toolbar      toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career);
        init();
        setData();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        rvLevelList = findViewById(R.id.rvLevelList);
    }

    private void setData() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        LevelAdapter levelAdapter = new LevelAdapter(this, GameSettings.getLevelList(this));
        rvLevelList.setLayoutManager(new LinearLayoutManager(this));
        rvLevelList.setAdapter(levelAdapter);
    }
}
