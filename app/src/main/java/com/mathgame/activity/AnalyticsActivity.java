package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mathgame.R;
import com.mathgame.model.Result;
import com.mathgame.structure.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;
import static com.mathgame.appdata.Keys.Extras.PLAYER_ONE_RESULT;
import static com.mathgame.appdata.Keys.Extras.PLAYER_TWO_RESULT;

public class AnalyticsActivity extends BaseActivity {
    private int[]    MATERIAL_COLORS = {rgb("#e666d02a"), rgb("#e6e2574c"), rgb("#4D000000")};
    private PieChart playerOneChart, playerTwoChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        init();
        setData();
    }

    private void setData() {
        Result firstPlayerResult = getIntent().getParcelableExtra(PLAYER_ONE_RESULT);
        Result secondPlayerResult = getIntent().getParcelableExtra(PLAYER_TWO_RESULT);

        if (firstPlayerResult != null) {
            List<PieEntry> NoOfEmp = new ArrayList<>();
            NoOfEmp.add(new PieEntry(firstPlayerResult.getCorrect(), 0));
            NoOfEmp.add(new PieEntry(firstPlayerResult.getIncorrect(), 1));
            NoOfEmp.add(new PieEntry(firstPlayerResult.getUnAttempted(), 2));
            PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");
            PieData data = new PieData(dataSet);
            playerOneChart.setData(data);
            dataSet.setColors(MATERIAL_COLORS);
            playerOneChart.animateXY(1000, 1000);
        }
        if (secondPlayerResult != null) {
            List<PieEntry> NoOfEmp = new ArrayList<>();
            NoOfEmp.add(new PieEntry(secondPlayerResult.getCorrect(), 0));
            NoOfEmp.add(new PieEntry(secondPlayerResult.getIncorrect(), 1));
            NoOfEmp.add(new PieEntry(secondPlayerResult.getUnAttempted(), 2));
            PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");
            PieData data = new PieData(dataSet);
            playerTwoChart.setData(data);
            dataSet.setColors(MATERIAL_COLORS);
            playerTwoChart.animateXY(1000, 1000);
        }
    }

    private void init() {
        playerOneChart = findViewById(R.id.playerOneChart);
        playerTwoChart = findViewById(R.id.playerTwoChart);
    }


}
