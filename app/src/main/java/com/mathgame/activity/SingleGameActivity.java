package com.mathgame.activity;

import android.os.Build;
import android.os.Bundle;
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
import com.mathgame.appdata.Dependencies;
import com.mathgame.dialog.GameCountdownDialog;
import com.mathgame.dialog.OptionsDialog;
import com.mathgame.model.CustomMode;
import com.mathgame.model.GameResult;
import com.mathgame.model.Question;
import com.mathgame.plugin.CountDownTimerWithPause;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.QuestionUtils;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mathgame.appdata.Constant.QUESTION_DELAY_TIME;

public class SingleGameActivity extends BaseActivity implements View.OnClickListener {
    private RoundedHorizontalProgressBar pbTimer;
    private CustomMode                   customMode;
    private View                         vMultipleChoice;
    private View                         vGameYesOrNo;
    private View                         vInputAnswer;
    private TextView                     tvSkipToNext;
    private TextView                     tvTimerValue;
    private TextView                     tvNumberOfQuestion;
    private TextView                     tvQuestion;
    private int                          remainingQuestion = 0;
    private TextView                     tvOption1, tvOption2, tvOption3, tvOption4;
    private Question                currentQuestion;
    private CountDownTimerWithPause countDownTimer;
    private CardView                cvCorrect, cvIncorrect;
    private int        skipNumbers;
    private GameResult gameResult;
    private boolean    isGameStated;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setData();
        new GameCountdownDialog.Builder(this)
                .message(R.string.game_starting_in)
                .listener(new GameCountdownDialog.Listener() {
                    @Override
                    public void performPositiveAction(int purpose, Bundle backpack) {
                        startGame();
                        isGameStated = true;
                    }

                    @Override
                    public void performNegativeAction(int purpose, Bundle backpack) {
                        isGameStated = false;
                        onBackPressed();
                    }
                }).build().show();
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_single_game;
    }

    private void init() {
        pbTimer = findViewById(R.id.pbTimer);
        ImageView ivBack = findViewById(R.id.ivBack);
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
        cvCorrect = findViewById(R.id.cvCorrect);
        cvIncorrect = findViewById(R.id.cvIncorrect);
        Utils.setOnClickListener(this, ivBack, tvOption1, tvOption2, tvOption3, tvOption4, cvCorrect, cvIncorrect, tvSkipToNext);
    }

    private void setData() {
        Dependencies.setSinglePlayerResult(this, null);
        gameResult = new GameResult();
        customMode = Objects.requireNonNull(getIntent().getExtras()).getParcelable(CustomMode.class.getName());
        Utils.logRequestBody(customMode);

        if (customMode.getGameType() == Codes.GameType.MANUAL_INPUT.value) {
            vInputAnswer.setVisibility(View.VISIBLE);
        } else if (customMode.getGameType() == Codes.GameType.YES_NO.value) {
            vGameYesOrNo.setVisibility(View.VISIBLE);
        } else {
            vMultipleChoice.setVisibility(View.VISIBLE);
        }
        skipNumbers = customMode.getSkipNumbers();
        if (skipNumbers == 0) {
            tvSkipToNext.setVisibility(View.GONE);
        } else {
            tvSkipToNext.setVisibility(View.VISIBLE);
            tvSkipToNext.setText(String.format("(%d) %s", skipNumbers, getString(R.string.skip_to_next)));
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
        remainingQuestion++;
        tvNumberOfQuestion.setText(String.format(locale(), "%d/%d", remainingQuestion, customMode.getNumberOfQuestions()));
        tvOption1.setBackgroundResource(R.drawable.bg_multiple_choice);
        tvOption2.setBackgroundResource(R.drawable.bg_multiple_choice);
        tvOption3.setBackgroundResource(R.drawable.bg_multiple_choice);
        tvOption4.setBackgroundResource(R.drawable.bg_multiple_choice);
        cvCorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        cvIncorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        if (skipNumbers == 0) {
            tvSkipToNext.setVisibility(View.GONE);
        } else {
            tvSkipToNext.setVisibility(View.VISIBLE);
            tvSkipToNext.setText(String.format("(%d) %s", skipNumbers, getString(R.string.skip_to_next)));
        }

        if (remainingQuestion <= customMode.getNumberOfQuestions()) {
            if (customMode.getQuestionSample().isEmpty()) {
                currentQuestion = QuestionUtils.getQuestionWithAnswer(customMode);
            } else {
                currentQuestion = QuestionUtils.getLevelQuestionWithAnswer(customMode);
            }
            tvQuestion.setText(currentQuestion.getQuestion());
            if (customMode.getGameType() == Codes.GameType.MULTIPLE_CHOICE.value) {
                tvOption1.setText(currentQuestion.getOption_1());
                tvOption2.setText(currentQuestion.getOption_2());
                tvOption3.setText(currentQuestion.getOption_3());
                tvOption4.setText(currentQuestion.getOption_4());
            }
            if (customMode.getTimerValue() > 0) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                countDownTimer = new CountDownTimerWithPause(customMode.getTimerValue() * 1000, 100, true) {
                    @Override
                    public void onTick(long millis) {
                        String hms = String.format(locale(), "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit
                                .MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                        tvTimerValue.setText(String.format(locale(), "%s : %s", getString(R.string.timer), hms));
                        // Log.e(TAG, "Millisecond :: " + millis);
                        int progress = (int) ((millis * 100) / (customMode.getTimerValue() * 1000));
                        //  Log.e(TAG, "Progress :: " + progress);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            pbTimer.setProgress(progress, true);
                        } else {
                            pbTimer.setProgress(progress);
                        }
                    }

                    @Override
                    public void onFinish() {
                        startGame();
                    }
                };
                countDownTimer.create();
            }
        } else {
            Transition.transit(this, SingleGameResultActivity.class);
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
            case R.id.cvCorrect:
                onCorrectClicked();
                break;
            case R.id.cvIncorrect:
                onIncorrectClicked();
                break;
            case R.id.tvSkipToNext:
                onQuestionSkipped();
                break;
        }
    }

    private void onQuestionSkipped() {
        skipNumbers--;
        saveForAnalytics(Constant.AnswerType.SKIPPED, currentQuestion, "-");
        startGame();
    }

    private void saveForAnalytics(int answerType, Question mQuestion, String answer) {
        mQuestion.setAnswerType(answerType);
        mQuestion.setUserInput(answer);
        ArrayList<Question> questionList = gameResult.getQuestionList();
        boolean isFound = false;
        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            if (question.getId().equalsIgnoreCase(mQuestion.getId())) {
                questionList.set(i, mQuestion);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            questionList.add(mQuestion);
        }
        gameResult.setQuestionList(questionList);
        Dependencies.setSinglePlayerResult(this, gameResult);
    }

    private void onCorrectClicked() {
        if (currentQuestion.isCorrect()) {
            saveForAnalytics(Constant.AnswerType.CORRECT, currentQuestion, getString(R.string.yes_text));
            cvIncorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            cvCorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_bg_color_success));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startGame();
                }
            }, QUESTION_DELAY_TIME);
        } else {
            saveForAnalytics(Constant.AnswerType.INCORRECT, currentQuestion, getString(R.string.no_text));
            cvCorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_bg_color_error));
        }
    }

    private void onIncorrectClicked() {
        if (!currentQuestion.isCorrect()) {
            saveForAnalytics(Constant.AnswerType.CORRECT, currentQuestion, getString(R.string.yes_text));
            cvCorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            cvIncorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_bg_color_success));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startGame();
                }
            }, QUESTION_DELAY_TIME);
        } else {
            saveForAnalytics(Constant.AnswerType.INCORRECT, currentQuestion, getString(R.string.no_text));
            cvIncorrect.setCardBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_bg_color_error));
        }
    }

    private void onOptionClicked(String answer, TextView tvOption) {
        if (currentQuestion.getAnswer().equals(answer)) {
            saveForAnalytics(Constant.AnswerType.CORRECT, currentQuestion, currentQuestion.getAnswer());
            tvOption.setBackgroundResource(R.drawable.bg_correct_answer);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startGame();
                }
            }, QUESTION_DELAY_TIME);
        } else {
            saveForAnalytics(Constant.AnswerType.INCORRECT, currentQuestion, answer);
            Utils.vibrate(this);
            tvOption.setBackgroundResource(R.drawable.bg_incorrect_anwer);
        }
    }

    @Override
    public void onBackPressed() {
        if (isGameStated) {
            if (countDownTimer != null) {
                countDownTimer.pause();
            }
            new OptionsDialog.Builder(this)
                    .message(R.string.are_you_sure_you_want_to_exit_the_game)
                    .positiveButton(R.string.yes_text)
                    .negativeButton(R.string.no_text)
                    .listener(new OptionsDialog.Listener() {
                        @Override
                        public void performPositiveAction() {
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            Transition.exit(SingleGameActivity.this);
                        }

                        @Override
                        public void performNegativeAction() {
                            countDownTimer.resume();
                        }
                    }).build().show();
        } else if (countDownTimer != null) {
            countDownTimer.cancel();
            Transition.exit(SingleGameActivity.this);
        } else {
            Transition.exit(SingleGameActivity.this);
        }
    }
}
