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
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class DualGameResultActivity extends BaseActivity implements View.OnClickListener {
    private int[]    MATERIAL_COLORS = {rgb("#e666d02a"), rgb("#e6e2574c"), rgb("#4D000000")};
    private PieChart playerOneChart, playerTwoChart;
    private GameResult playerOneResult, playerTwoResult;
    private TextView tvResultFeedback, tvPlayer1, tvPlayer2;

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
        return R.layout.activity_dual_game_result;
    }

    private void init() {
        tvResultFeedback = findViewById(R.id.tvResultFeedback);
        playerOneChart = findViewById(R.id.playerOneChart);
        playerTwoChart = findViewById(R.id.playerTwoChart);
        tvPlayer1 = findViewById(R.id.tvPlayer1);
        tvPlayer2 = findViewById(R.id.tvPlayer2);
        TextView tvSeeAllQuestions = findViewById(R.id.tvSeeAllQuestions);
        Utils.setOnClickListener(this, tvSeeAllQuestions);
    }

    private void setData() {
        playerOneResult = Dependencies.getSinglePlayerResult(this);
        playerTwoResult = Dependencies.getSecondPlayerResult(this);
        int playerOneCorrect = 0, playerTwoCorrect = 0;
        if (playerOneResult != null) {
            ArrayList<Question> questionList = playerOneResult.getQuestionList();
            int incorrect = 0, unattempted = 0;
            for (Question question : questionList) {
                if (question.getAnswerType() == Constant.AnswerType.CORRECT) {
                    playerOneCorrect++;
                } else if (question.getAnswerType() == Constant.AnswerType.INCORRECT) {
                    incorrect++;
                } else {
                    unattempted++;
                }
            }

            List<PieEntry> noOfEmp = new ArrayList<>();
            if (playerOneCorrect > 0) {
                noOfEmp.add(new PieEntry(playerOneCorrect, 0));
            }
            if (incorrect > 0) {
                noOfEmp.add(new PieEntry(incorrect, 1));
            }
            if (unattempted > 0) {
                noOfEmp.add(new PieEntry(unattempted, 2));
            }
            PieDataSet dataSet = new PieDataSet(noOfEmp, getString(R.string.question_result));
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
        if (playerTwoResult != null) {
            ArrayList<Question> questionList = playerTwoResult.getQuestionList();
            int incorrect = 0, unattempted = 0;
            for (Question question : questionList) {
                if (question.getAnswerType() == Constant.AnswerType.CORRECT) {
                    playerTwoCorrect++;
                } else if (question.getAnswerType() == Constant.AnswerType.INCORRECT) {
                    incorrect++;
                } else {
                    unattempted++;
                }
            }

            List<PieEntry> noOfEmp = new ArrayList<>();
            if (playerTwoCorrect > 0) {
                noOfEmp.add(new PieEntry(playerTwoCorrect, 0));
            }
            if (incorrect > 0) {
                noOfEmp.add(new PieEntry(incorrect, 1));
            }
            if (unattempted > 0) {
                noOfEmp.add(new PieEntry(unattempted, 2));
            }
            PieDataSet dataSet = new PieDataSet(noOfEmp, getString(R.string.question_result));
            dataSet.setValueTextSize(20f);
            dataSet.setValueTextColor(ContextCompat.getColor(this, R.color.white));
            PieData data = new PieData(dataSet);
            LegendEntry l1 = new LegendEntry(getString(R.string.correct), Legend.LegendForm.SQUARE, 10f, 2f, null, MATERIAL_COLORS[0]);
            LegendEntry l2 = new LegendEntry(getString(R.string.incorrect), Legend.LegendForm.SQUARE, 10f, 2f, null, MATERIAL_COLORS[1]);
            LegendEntry l3 = new LegendEntry(getString(R.string.unattempted), Legend.LegendForm.SQUARE, 10f, 2f, null, MATERIAL_COLORS[2]);

            playerTwoChart.getLegend().setCustom(new LegendEntry[]{l1, l2, l3});
            playerTwoChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            playerTwoChart.getLegend().setTextColor(ContextCompat.getColor(this, R.color.white));
            playerTwoChart.getDescription().setEnabled(false);
            playerTwoChart.getLegend().setTextSize(13f);
            playerTwoChart.getLegend().setFormToTextSpace(5f);
            playerTwoChart.setData(data);
            dataSet.setColors(MATERIAL_COLORS);
            playerTwoChart.animateXY(1000, 1000);
        }
        if (playerOneCorrect > playerTwoCorrect) {
            tvResultFeedback.setText(R.string.player_1_wins);
            tvPlayer1.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.ic_winner), null, null);
            tvPlayer2.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.ic_loser), null, null);
        } else if (playerOneCorrect < playerTwoCorrect) {
            tvResultFeedback.setText(R.string.player_2_wins);
            tvPlayer2.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.ic_winner), null, null);
            tvPlayer1.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.ic_loser), null, null);
        } else {
            tvResultFeedback.setText(R.string.its_a_draw);
            tvPlayer2.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.ic_winner), null, null);
            tvPlayer1.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.ic_winner), null, null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSeeAllQuestions:
                Bundle bundle = new Bundle();
                ArrayList<Question> questionList = playerOneResult.getQuestionList();
                questionList.addAll(playerTwoResult.getQuestionList());
                bundle.putParcelableArrayList(Question.class.getName(), questionList);
                Transition.transit(this, AnswerListActivity.class, bundle);
                break;
        }
    }

}
