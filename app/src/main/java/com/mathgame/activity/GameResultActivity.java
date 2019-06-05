package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;
import com.mathgame.R;
import com.mathgame.structure.BaseActivity;

public class GameResultActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PieChart pieChartPlayerOne = findViewById(R.id.pieChartPlayerOne);
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_game_result;
    }
}
