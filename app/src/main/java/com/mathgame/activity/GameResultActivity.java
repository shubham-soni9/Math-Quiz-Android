package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;
import com.mathgame.R;
import com.mathgame.structure.BaseActivity;

public class GameResultActivity extends BaseActivity {
    private PieChart pieChartPlayerOne;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);
        pieChartPlayerOne=findViewById(R.id.pieChartPlayerOne);

    }
}
