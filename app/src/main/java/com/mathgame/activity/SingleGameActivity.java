package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Codes;
import com.mathgame.model.CustomMode;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.Objects;

public class SingleGameActivity extends BaseActivity implements View.OnClickListener {
    private RoundedHorizontalProgressBar pbTimer;
    private ImageView                    ivBack;
    private CustomMode                   customMode;
    private View                         vMultipleChoice;
    private View                         vGameYesOrNo;
    private View                         vInputAnswer;
    private TextView                     tvSkipToNext;
    private TextView                     tvTimerValue;
    private TextView                     tvNumberOfQuestion;
    private TextView                     tvQuestion;
    private int                          remainingQuestion = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);
        init();
        // pbTimer.animateProgress(8000, 0, 100);
        Utils.setOnClickListener(this, ivBack);
        setData();
        startGame();
    }

    private void init() {
        pbTimer = findViewById(R.id.pbTimer);
        ivBack = findViewById(R.id.ivBack);
        tvQuestion = findViewById(R.id.tvQuestion);
        vMultipleChoice = findViewById(R.id.vMultipleChoice);
        vGameYesOrNo = findViewById(R.id.vGameYesOrNo);
        vInputAnswer = findViewById(R.id.vInputAnswer);
        tvSkipToNext = findViewById(R.id.tvSkipToNext);
        tvTimerValue = findViewById(R.id.tvTimerValue);
        tvNumberOfQuestion = findViewById(R.id.tvNumberOfQuestion);
    }

    private void setData() {
        customMode = Objects.requireNonNull(getIntent().getExtras()).getParcelable(CustomMode.class.getName());
        Utils.logRequestBody(customMode);

        if (customMode.getGameType() == Codes.GameType.MANUAL_INPUT.value) {
            vInputAnswer.setVisibility(View.VISIBLE);
        } else if (customMode.getGameType() == Codes.GameType.YES_NO.value) {
            vGameYesOrNo.setVisibility(View.VISIBLE);
        } else {
            vMultipleChoice.setVisibility(View.VISIBLE);
        }

        if (customMode.getSkipNumbers() == 0) {
            tvSkipToNext.setVisibility(View.GONE);
        } else {
            tvSkipToNext.setVisibility(View.VISIBLE);
            tvSkipToNext.setText(String.format(locale(), "(%d) %s", customMode.getSkipNumbers(), getString(R.string.skip_to_next)));
        }

        if (customMode.getTimerValue() == 0) {
            tvTimerValue.setVisibility(View.GONE);
            pbTimer.setVisibility(View.GONE);
        } else {
            pbTimer.setVisibility(View.VISIBLE);
            tvTimerValue.setVisibility(View.VISIBLE);
            tvTimerValue.setText(String.format(locale(), "%s : %d", getString(R.string.timer), customMode.getTimerValue()));
        }

        tvNumberOfQuestion.setText(String.format(locale(), "%d/%d", remainingQuestion, customMode.getNumberOfQuestions()));
    }

    private void startGame() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Transition.exit(this);
    }
}
