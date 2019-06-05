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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setData();
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.career_mode);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_career;
    }

    private void init() {
        rvLevelList = findViewById(R.id.rvLevelList);
    }

    private void setData() {
        LevelAdapter levelAdapter = new LevelAdapter(this, GameSettings.getLevelList(this));
        rvLevelList.setLayoutManager(new LinearLayoutManager(this));
        rvLevelList.setAdapter(levelAdapter);
    }
}
