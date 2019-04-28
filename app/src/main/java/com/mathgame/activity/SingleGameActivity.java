package com.mathgame.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Codes;
import com.mathgame.model.CustomMode;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Log;
import com.mathgame.util.QuestionUtils;
import com.mathgame.util.RandomUtils;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    private TextView                     tvOption1, tvOption2, tvOption3, tvOption4;
    private              Pair<String, String> currentQna;
    private              CountDownTimer       countDownTimer;
    private static final String               TAG = SingleGameActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);
        init();
        // pbTimer.animateProgress(8000, 0, 100);
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
        tvOption1 = findViewById(R.id.tvOption1);
        tvOption2 = findViewById(R.id.tvOption2);
        tvOption3 = findViewById(R.id.tvOption3);
        tvOption4 = findViewById(R.id.tvOption4);
        tvNumberOfQuestion = findViewById(R.id.tvNumberOfQuestion);
        Utils.setOnClickListener(this, ivBack, tvOption1, tvOption2, tvOption3, tvOption4);
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
        tvOption1.setBackgroundResource(R.drawable.background_multiple_choice);
        tvOption2.setBackgroundResource(R.drawable.background_multiple_choice);
        tvOption3.setBackgroundResource(R.drawable.background_multiple_choice);
        tvOption4.setBackgroundResource(R.drawable.background_multiple_choice);

        tvNumberOfQuestion.setText(String.format(locale(), "%d/%d", remainingQuestion, customMode.getNumberOfQuestions()));
        if (remainingQuestion <= customMode.getNumberOfQuestions()) {
            currentQna = QuestionUtils.getQuestionWithAnswer(customMode);
            tvQuestion.setText(currentQna.first);
            if (customMode.getGameType() == Codes.GameType.MULTIPLE_CHOICE.value) {
                ArrayList<String> options = new ArrayList<>();
                options.add(currentQna.second);
                for (int i = 0; i < 3; i++) {
                    String wrongOption = String.valueOf(RandomUtils.getRandomInteger(20, 2));
                    for (int j = 0; j < options.size(); j++) {
                        String value = options.get(j);
                        if (wrongOption.equals(value)) {
                            wrongOption = String.valueOf(RandomUtils.getRandomInteger(20, 2));
                            j = 0;
                        }
                    }
                    options.add(wrongOption);
                }
                Collections.shuffle(options);
                tvOption1.setText(options.get(0));
                tvOption2.setText(options.get(1));
                tvOption3.setText(options.get(2));
                tvOption4.setText(options.get(3));
            }
            if (customMode.getTimerValue() > 0) {
                if (customMode.getTimerType() == Codes.TimerType.PER_QUESTION.value) {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    countDownTimer = new CountDownTimer(customMode.getTimerValue() * 1000, 100) {
                        @Override
                        public void onTick(long millis) {
                            String hms = String.format(locale(), "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit
                                    .MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                            tvTimerValue.setText(String.format(locale(), "%s : %s", getString(R.string.timer), hms));
                           // Log.e(TAG, "Millisecond :: " + millis);
                            int progress = (int) ((millis * 100) / (customMode.getTimerValue() * 1000));
                          //  Log.e(TAG, "Progress :: " + progress);
                            pbTimer.setProgress(progress, true);
                        }

                        @Override
                        public void onFinish() {
                            remainingQuestion++;
                            startGame();
                        }
                    };
                    countDownTimer.start();
                }
            }
        } else {
            onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tvOption1:
                onOptionClicked(Utils.get(tvOption1), tvOption1);
                break;
            case R.id.tvOption2:
                onOptionClicked(Utils.get(tvOption2), tvOption2);
                break;
            case R.id.tvOption3:
                onOptionClicked(Utils.get(tvOption3), tvOption3);
                break;
            case R.id.tvOption4:
                onOptionClicked(Utils.get(tvOption4), tvOption4);
                break;
        }
    }

    private void onOptionClicked(String answer, TextView tvOption) {
        if (currentQna.second.equals(answer)) {
            tvOption.setBackgroundResource(R.drawable.background_correct_answer);
            remainingQuestion++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startGame();
                }
            }, 500);
        } else {
            Utils.vibrate(this);
            tvOption.setBackgroundResource(R.drawable.background_incorrect_anwer);
        }
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        Transition.exit(this);
    }
}
