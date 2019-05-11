package com.mathgame.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Codes;
import com.mathgame.appdata.Constant;
import com.mathgame.model.CustomMode;
import com.mathgame.model.Question;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.QuestionUtils;
import com.mathgame.util.RandomUtils;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;
import com.mathgame.util.ViewUtils;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DualGameActivity extends BaseActivity implements View.OnClickListener {
    private static final String                       TAG = DualGameActivity.class.getName();
    private              RoundedHorizontalProgressBar pbPlayer1Timer, pbPlayer2Timer;
    private ImageView  ivBack;
    private CustomMode customMode;
    private View       vPlayer1MultipleChoice, vPlayer2MultipleChoice;
    private View vPlayer1GameYesOrNo, vPlayer2GameYesOrNo;
    private TextView tvPlayer1TimerValue, tvPlayer2TimerValue;
    private TextView tvNumberOfQuestion;
    private TextView tvPlayer1Question, tvPlayer2Question;
    private int      remainingQuestion = 1;
    private TextView tvPlayer1Option1, tvPlayer1Option2, tvPlayer1Option3, tvPlayer1Option4;
    private TextView tvPlayer2Option1, tvPlayer2Option2, tvPlayer2Option3, tvPlayer2Option4;
    private Question       currentQuestion;
    private CountDownTimer countDownTimer;
    private CardView       cvPlayer1Correct, cvPlayer1Incorrect, cvPlayer2Correct, cvPlayer2Incorrect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_game);
        init();
        setData();
        startGame();
    }

    private void init() {
        ivBack = findViewById(R.id.ivBack);
        pbPlayer1Timer = findViewById(R.id.pbPlayer1Timer);
        pbPlayer2Timer = findViewById(R.id.pbPlayer2Timer);
        tvPlayer1Option1 = findViewById(R.id.tvPlayer1Option1);
        tvPlayer1Option2 = findViewById(R.id.tvPlayer1Option2);
        tvPlayer1Option3 = findViewById(R.id.tvPlayer1Option3);
        tvPlayer1Option4 = findViewById(R.id.tvPlayer1Option4);
        tvPlayer2Option1 = findViewById(R.id.tvPlayer2Option1);
        tvPlayer2Option2 = findViewById(R.id.tvPlayer2Option2);
        tvPlayer2Option3 = findViewById(R.id.tvPlayer2Option3);
        tvPlayer2Option4 = findViewById(R.id.tvPlayer2Option4);
        tvPlayer1Question = findViewById(R.id.tvPlayer1Question);
        tvPlayer2Question = findViewById(R.id.tvPlayer2Question);
        tvPlayer1TimerValue = findViewById(R.id.tvPlayer1TimerValue);
        tvPlayer2TimerValue = findViewById(R.id.tvPlayer2TimerValue);
        vPlayer1GameYesOrNo = findViewById(R.id.vPlayer1GameYesOrNo);
        vPlayer2GameYesOrNo = findViewById(R.id.vPlayer2GameYesOrNo);
        vPlayer1MultipleChoice = findViewById(R.id.vPlayer1MultipleChoice);
        vPlayer2MultipleChoice = findViewById(R.id.vPlayer2MultipleChoice);
        tvNumberOfQuestion = findViewById(R.id.tvNumberOfQuestion);
        cvPlayer1Correct = findViewById(R.id.cvPlayer1Correct);
        cvPlayer2Correct = findViewById(R.id.cvPlayer2Correct);
        cvPlayer1Incorrect = findViewById(R.id.cvPlayer1Incorrect);
        cvPlayer2Incorrect = findViewById(R.id.cvPlayer2Incorrect);
        Utils.setOnClickListener(this, ivBack, tvPlayer1Option1, tvPlayer1Option2, tvPlayer1Option3, tvPlayer1Option4,
                                 tvPlayer2Option1, tvPlayer2Option2, tvPlayer2Option3, tvPlayer2Option4, cvPlayer1Correct,
                                 cvPlayer1Incorrect, cvPlayer2Correct, cvPlayer2Incorrect);
    }

    private void setData() {
        customMode = Objects.requireNonNull(getIntent().getExtras()).getParcelable(CustomMode.class.getName());
        Utils.logRequestBody(customMode);

        if (customMode.getGameType() == Codes.GameType.YES_NO.value) {
            ViewUtils.setVisibility(View.VISIBLE, vPlayer1GameYesOrNo, vPlayer2GameYesOrNo);
        } else {
            ViewUtils.setVisibility(View.VISIBLE, vPlayer1MultipleChoice, vPlayer2MultipleChoice);
        }

        if (customMode.getTimerValue() == 0) {
            ViewUtils.setVisibility(View.GONE, tvPlayer1TimerValue, tvPlayer2TimerValue, pbPlayer1Timer, pbPlayer2Timer);
        } else {
            ViewUtils.setVisibility(View.VISIBLE, pbPlayer1Timer, pbPlayer2Timer, tvPlayer1TimerValue, tvPlayer2TimerValue);
            tvPlayer1TimerValue.setText(String.format(locale(), "%s : %d", getString(R.string.timer), customMode.getTimerValue()));
            tvPlayer2TimerValue.setText(String.format(locale(), "%s : %d", getString(R.string.timer), customMode.getTimerValue()));
        }

        tvNumberOfQuestion.setText(String.format(locale(), "%d/%d", remainingQuestion, customMode.getNumberOfQuestions()));
    }

    private void startGame() {
        ViewUtils.setBackgroundResource(R.drawable.background_multiple_choice, tvPlayer1Option1, tvPlayer1Option2, tvPlayer1Option3,
                                        tvPlayer1Option4, tvPlayer2Option1, tvPlayer2Option2, tvPlayer2Option3, tvPlayer2Option4);
        ViewUtils.setCardBackgroundColor(this, R.color.colorPrimary, cvPlayer1Correct, cvPlayer1Incorrect, cvPlayer2Correct,
                                         cvPlayer2Incorrect);

        tvNumberOfQuestion.setText(String.format(locale(), "%d/%d", remainingQuestion, customMode.getNumberOfQuestions()));
        if (remainingQuestion <= customMode.getNumberOfQuestions()) {
            currentQuestion = QuestionUtils.getQuestionWithAnswer(customMode);
            tvPlayer1Question.setText(currentQuestion.getQuestion());
            tvPlayer2Question.setText(currentQuestion.getQuestion());
            if (customMode.getGameType() == Codes.GameType.MULTIPLE_CHOICE.value) {
                ArrayList<String> options = new ArrayList<>();
                options.add(currentQuestion.getAnswer());
                int maximum = 99;
                int minimum = 2;
                switch (currentQuestion.getOperation()) {
                    case Constant.MathSign.ADDITION:
                        maximum = currentQuestion.getA() + currentQuestion.getB();
                        minimum = currentQuestion.getB();
                        break;
                    case Constant.MathSign.SUBTRACTION:
                        maximum = currentQuestion.getA() - currentQuestion.getB();
                        minimum = currentQuestion.getB();
                        break;
                    case Constant.MathSign.MULTIPLICATION:
                        maximum = currentQuestion.getA() * currentQuestion.getB();
                        minimum = currentQuestion.getA();
                        break;
                    case Constant.MathSign.DIVISION:
                        maximum = currentQuestion.getA();
                        minimum = currentQuestion.getB();
                        break;
                }

                while ((maximum - minimum) < 4) {
                    maximum = ++maximum;
                    minimum = --minimum;
                }

                for (int i = 0; i < 3; i++) {
                    String wrongOption = String.valueOf(RandomUtils.getRandomInt(maximum, minimum));
                    for (int j = 0; j < options.size(); j++) {
                        String value = options.get(j);
                        if (wrongOption.equals(value)) {
                            wrongOption = String.valueOf(RandomUtils.getRandomInt(maximum, minimum));
                            j = 0;
                        }
                    }
                    options.add(wrongOption);
                }
                Collections.shuffle(options);
                tvPlayer1Option1.setText(options.get(0));
                tvPlayer1Option2.setText(options.get(1));
                tvPlayer1Option3.setText(options.get(2));
                tvPlayer1Option4.setText(options.get(3));
                tvPlayer2Option1.setText(options.get(0));
                tvPlayer2Option2.setText(options.get(1));
                tvPlayer2Option3.setText(options.get(2));
                tvPlayer2Option4.setText(options.get(3));
            }
            if (customMode.getTimerValue() > 0) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                countDownTimer = new CountDownTimer(customMode.getTimerValue() * 1000, 100) {
                    @Override
                    public void onTick(long millis) {
                        String hms = String.format(locale(), "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit
                                .MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                        tvPlayer1TimerValue.setText(String.format(locale(), "%s : %s", getString(R.string.timer), hms));
                        tvPlayer2TimerValue.setText(String.format(locale(), "%s : %s", getString(R.string.timer), hms));
                        // Log.e(TAG, "Millisecond :: " + millis);
                        int progress = (int) ((millis * 100) / (customMode.getTimerValue() * 1000));
                        //  Log.e(TAG, "Progress :: " + progress);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            pbPlayer1Timer.setProgress(progress, true);
                            pbPlayer2Timer.setProgress(progress, true);
                        } else {
                            pbPlayer1Timer.setProgress(progress);
                            pbPlayer2Timer.setProgress(progress);
                        }
                    }

                    @Override
                    public void onFinish() {
                        remainingQuestion++;
                        startGame();
                    }
                };
                countDownTimer.start();
            }
        } else {
            onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tvPlayer1Option1:
            case R.id.tvPlayer1Option2:
            case R.id.tvPlayer1Option3:
            case R.id.tvPlayer1Option4:
            case R.id.tvPlayer2Option1:
            case R.id.tvPlayer2Option2:
            case R.id.tvPlayer2Option3:
            case R.id.tvPlayer2Option4:
                onOptionClicked((TextView) view);
                break;
            case R.id.cvPlayer1Correct:
                onCorrectClicked(cvPlayer1Correct, cvPlayer1Incorrect);
                break;
            case R.id.cvPlayer1Incorrect:
                onIncorrectClicked(cvPlayer1Correct, cvPlayer1Incorrect);
                break;
            case R.id.cvPlayer2Correct:
                onCorrectClicked(cvPlayer2Correct, cvPlayer2Incorrect);
                break;
            case R.id.cvPlayer2Incorrect:
                onIncorrectClicked(cvPlayer2Correct, cvPlayer2Incorrect);
                break;
        }
    }

    private void onCorrectClicked(CardView cvCorrect, CardView cvIncorrect) {
        if (currentQuestion.isCorrect()) {
            cvIncorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            cvCorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_bg_color_success));
            remainingQuestion++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startGame();
                }
            }, 500);
        } else {
            cvCorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_bg_color_error));
        }
    }

    private void onIncorrectClicked(CardView cvCorrect, CardView cvIncorrect) {
        if (!currentQuestion.isCorrect()) {
            cvCorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            cvIncorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_bg_color_success));
            remainingQuestion++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startGame();
                }
            }, 500);
        } else {
            cvIncorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_bg_color_error));
        }
    }

    private void onOptionClicked(TextView tvOption) {
        if (currentQuestion.getAnswer().equals(Utils.get(tvOption))) {
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
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Transition.exit(this);
    }

    private void saveForAnalytics() {

    }
}
