package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class SingleGameResultActivity extends BaseActivity implements View.OnClickListener {
    private int[]    MATERIAL_COLORS = {rgb("#e666d02a"), rgb("#e6e2574c"), rgb("#4D000000")};
    private PieChart playerOneChart, playerTwoChart;
    private TextView tvTotal, tvAttempted, tvCorrect, tvInCorrect, tvPlayerScore, tvFeedBack, tvSeeAllQuestions;
    private ProgressWheel pbCorrectWheel, pbInCorrectWheel;
    private GameResult gameResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setData();
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.analytics);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_single_game_result;
    }

    private void init() {
        playerOneChart = findViewById(R.id.resultPieChart);
        tvTotal = findViewById(R.id.tvTotal);
        tvAttempted = findViewById(R.id.tvAttempted);
        tvCorrect = findViewById(R.id.tvCorrect);
        tvInCorrect = findViewById(R.id.tvInCorrect);
        tvPlayerScore = findViewById(R.id.tvPlayerScore);
        tvFeedBack = findViewById(R.id.tvFeedBack);
        tvSeeAllQuestions = findViewById(R.id.tvSeeAllQuestions);
        pbCorrectWheel = findViewById(R.id.pbCorrectWheel);
        pbInCorrectWheel = findViewById(R.id.pbInCorrectWheel);
        Utils.setOnClickListener(this, tvSeeAllQuestions);
    }

    private void setData() {
        gameResult = Dependencies.getSinglePlayerResult(this);
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
            tvAttempted.setText(String.format("%02d", total - unattempted));
            tvCorrect.setText(String.format("%02d", correct));
            tvInCorrect.setText(String.format("%02d", incorrect));

            float score = (correct / (float) total) * 100;
            tvPlayerScore.setText(String.format("%s  :  %s%%", getString(R.string.player_score), score));

            pbCorrectWheel.setStepCountText(String.valueOf(correct));
            int correctPercentage = (correct / total) * 360;
            pbCorrectWheel.setPercentage(correctPercentage);

            int inCorrectPercentage = (incorrect / total) * 360;
            pbInCorrectWheel.setPercentage(inCorrectPercentage);
            pbInCorrectWheel.setStepCountText(String.valueOf(incorrect));

            List<PieEntry> NoOfEmp = new ArrayList<>();
            if (correct > 0) {
                NoOfEmp.add(new PieEntry(correct, 0));
            }
            if (incorrect > 0) {
                NoOfEmp.add(new PieEntry(incorrect, 1));
            }
            if (unattempted > 0) {
                NoOfEmp.add(new PieEntry(unattempted, 2));
            }
            PieDataSet dataSet = new PieDataSet(NoOfEmp, getString(R.string.question_result));
            dataSet.setValueTextSize(20f);
            dataSet.setValueTextColor(ContextCompat.getColor(this, R.color.white));
            PieData data = new PieData(dataSet);
            LegendEntry l1 = new LegendEntry(getString(R.string.correct), Legend.LegendForm.SQUARE, 10f, 2f, null, MATERIAL_COLORS[0]);
            LegendEntry l2 = new LegendEntry(getString(R.string.incorrect), Legend.LegendForm.SQUARE, 10f, 2f, null, MATERIAL_COLORS[1]);
            LegendEntry l3 = new LegendEntry(getString(R.string.unattempted), Legend.LegendForm.SQUARE, 10f, 2f, null, MATERIAL_COLORS[2]);

            playerOneChart.getLegend().setCustom(new LegendEntry[]{l1, l2, l3});
            playerOneChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            playerOneChart.getLegend().setTextColor(ContextCompat.getColor(this, R.color.white));
            playerOneChart.getDescription().setEnabled(false);
            playerOneChart.getLegend().setTextSize(13f);
            playerOneChart.getLegend().setFormToTextSpace(5f);
            playerOneChart.setData(data);
            dataSet.setColors(MATERIAL_COLORS);
            playerOneChart.animateXY(1000, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSeeAllQuestions:
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Question.class.getName(), gameResult.getQuestionList());
                Transition.transit(this, AnswerListActivity.class, bundle);
                break;
        }
    }

}
