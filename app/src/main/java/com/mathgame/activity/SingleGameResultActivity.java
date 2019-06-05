package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mathgame.R;
import com.mathgame.appdata.Constant;
import com.mathgame.appdata.Dependencies;
import com.mathgame.model.GameResult;
import com.mathgame.model.Question;
import com.mathgame.plugin.ProgressWheel;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class SingleGameResultActivity extends BaseActivity implements View.OnClickListener {
    private int[]    MATERIAL_COLORS = {rgb("#e666d02a"), rgb("#e6e2574c"), rgb("#4D000000")};
    private PieChart playerOneChart, playerTwoChart;
    private TextView tvTotal, tvAttempted, tvCorrect, tvInCorrect, tvPlayerScore,tvFeedBack,tvSeeAllQuestions;
    private ProgressWheel pbCorrectWheel, pbInCorrectWheel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game_result);
        init();
        setData();
    }

    private void init() {
        playerOneChart = findViewById(R.id.resultPieChart);
        tvTotal = findViewById(R.id.tvTotal);
        tvAttempted = findViewById(R.id.tvAttempted);
        tvCorrect = findViewById(R.id.tvCorrect);
        tvInCorrect = findViewById(R.id.tvInCorrect);
        tvPlayerScore = findViewById(R.id.tvPlayerScore);
        tvFeedBack=findViewById(R.id.tvFeedBack);
        tvSeeAllQuestions=findViewById(R.id.tvSeeAllQuestions);
        pbCorrectWheel = findViewById(R.id.pbCorrectWheel);
        pbInCorrectWheel = findViewById(R.id.pbInCorrectWheel);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.analytics));
        Utils.setOnClickListener(this,tvSeeAllQuestions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSeeAllQuestions:
                break;
        }
    }

    private void setData() {
        GameResult gameResult = Dependencies.getGameResult(this);
        if (gameResult != null) {
            ArrayList<Question> questionList = gameResult.getQuestionList();
            int correct = 0, incorrect = 0, unattempted = 0, total;
            total = questionList.size();
            for (Question question : questionList) {
                if (question.getAnswerType() == Constant.AnswerType.CORRECT) {
                    correct++;
                } else if (question.getAnswerType() == Constant.AnswerType.INCORRECT) {
                    incorrect++;
                } else {
                    unattempted++;
                }
            }

            tvTotal.setText(String.format("%02d", total));
            tvAttempted.setText(String.format("%02d",total - unattempted));
            tvCorrect.setText(String.format("%02d",correct));
            tvInCorrect.setText(String.format("%02d",incorrect));

            float score = (correct / (float) total) * 100;
            tvPlayerScore.setText(String.format("%s  :  %s%%", getString(R.string.player_score), score));

            pbCorrectWheel.setStepCountText(String.valueOf(correct));
            int correctPercentage = (correct / total) * 360;
            pbCorrectWheel.setPercentage(correctPercentage);

            int inCorrectPercentage = (incorrect / total) * 360;
            pbInCorrectWheel.setPercentage(inCorrectPercentage);
            pbInCorrectWheel.setStepCountText(String.valueOf(incorrect));

            List<PieEntry> NoOfEmp = new ArrayList<>();
            NoOfEmp.add(new PieEntry(correct, 0));
            NoOfEmp.add(new PieEntry(incorrect, 1));
            NoOfEmp.add(new PieEntry(unattempted, 2));
            PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");
            PieData data = new PieData(dataSet);
            playerOneChart.setData(data);
            dataSet.setColors(MATERIAL_COLORS);
            playerOneChart.animateXY(1000, 1000);
        }
    }

}
