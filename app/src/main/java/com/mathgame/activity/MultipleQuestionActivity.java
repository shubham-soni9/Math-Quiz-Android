package com.mathgame.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.customview.QuestionView;
import com.mathgame.dialog.GameCountdownDialog;
import com.mathgame.dialog.OptionsDialog;
import com.mathgame.listener.OnQuestionListener;
import com.mathgame.model.CustomMode;
import com.mathgame.model.Question;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.QuestionUtils;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;
import com.mathgame.util.ViewUtils;

import java.util.ArrayList;
import java.util.Objects;

public class MultipleQuestionActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, OnQuestionListener {
    private final int                 NUMBER_OF_QUESTION = 2;
    private       LinearLayout        llQuestionList;
    private       CustomMode          customMode;
    private       AppCompatSeekBar    slider;
    private       TextView            tvChangingStatus;
    private       LinearLayout        llSlider;
    private       ArrayList<Question> questionList;
    private       TextView            tvNumberOfQuestion;
    private       int                 remainingQuestion  = 0;
    private       boolean             isGameStated;

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
        return getString(R.string.app_name);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_multiple_question;
    }

    private void setData() {
        customMode = Objects.requireNonNull(getIntent().getExtras()).getParcelable(CustomMode.class.getName());
        questionList = new ArrayList<>();
    }

    private void init() {
        llQuestionList = findViewById(R.id.llQuestionList);
        RelativeLayout rlShimmerSlider = findViewById(R.id.rlShimmerSlider);
        slider = findViewById(R.id.slider);
        tvChangingStatus = findViewById(R.id.tvChangingStatus);
        llSlider = findViewById(R.id.llSlider);
        tvNumberOfQuestion = findViewById(R.id.tvNumberOfQuestion);
        slider.setOnSeekBarChangeListener(this);
        Utils.setOnClickListener(this, findViewById(R.id.ivBack));
    }


    private void startGame() {
        remainingQuestion++;
        tvNumberOfQuestion.setText(String.format(locale(), "%d/%d", remainingQuestion, customMode.getNumberOfQuestions()));
        if (remainingQuestion <= customMode.getNumberOfQuestions()) {
            View mQuestionView;
            llQuestionList.removeAllViews();
            questionList.clear();
            for (int i = 0; i < NUMBER_OF_QUESTION; i++) {
                questionList.add(QuestionUtils.getQuestionWithAnswer(customMode));
                mQuestionView = new QuestionView(this).render(questionList.get(i), i);
                llQuestionList.addView(mQuestionView);
            }
        } else {
            onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (isGameStated) {
            new OptionsDialog.Builder(this)
                    .message(R.string.are_you_sure_you_want_to_exit_the_game)
                    .positiveButton(R.string.yes_text)
                    .negativeButton(R.string.no_text)
                    .listener(new OptionsDialog.Listener() {
                        @Override
                        public void performPositiveAction() {
                            Transition.exit(MultipleQuestionActivity.this);
                        }

                        @Override
                        public void performNegativeAction() {
                        }
                    }).build().show();
        } else {
            Transition.exit(MultipleQuestionActivity.this);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        onSlide(seekBar.getProgress());
    }


    private void onSlide(int progress) {
        int initial = -50;
        llSlider.setVisibility(View.GONE);
        if (progress > 50) {
            tvChangingStatus.setVisibility(View.VISIBLE);
            tvChangingStatus.setBackgroundResource(R.color.snackbar_bg_color_success);
            tvChangingStatus.setText(R.string.next_question);
            tvChangingStatus.setAlpha(2 * ((float) (initial + progress) / 100.00f));
        } else if (progress < 50) {
            tvChangingStatus.setVisibility(View.VISIBLE);
            tvChangingStatus.setText(R.string.finish_test);
            tvChangingStatus.setBackgroundResource(R.color.snackbar_bg_color_error);
            tvChangingStatus.setAlpha(Math.abs(2 * ((float) (initial + progress) / 100.00f)));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getProgress() > 80) seekBar.setProgress(100);
        else if (seekBar.getProgress() < 20) seekBar.setProgress(0);
        else seekBar.setProgress(50);

        onSlideCompleted(seekBar.getProgress());
    }

    private void onSlideCompleted(int progress) {
        switch (progress) {
            case 0:
                onFinishTest();
                break;
            case 100:
                onNextQuestion();
                break;
            default:
                resetSliders();
                break;
        }
    }

    private boolean isValidate() {
        for (int i = 0; i < questionList.size(); i++) {
            QuestionView questionView = getQuestionView(i);
            if (!questionView.isCorrectAnswer()) {
                questionView.setError();
                return false;
            }
        }
        return true;
    }

    private void onNextQuestion() {
        resetSliders();
        if (isValidate()) {
            startGame();
        }
    }

    private void onFinishTest() {
        new OptionsDialog.Builder(this)
                .message(R.string.are_you_sure_you_want_to_finish_test)
                .listener(new OptionsDialog.Listener() {
                    @Override
                    public void performPositiveAction() {
                        onBackPressed();
                    }

                    @Override
                    public void performNegativeAction() {
                        resetSliders();
                    }
                }).build().show();
    }

    private void resetSliders() {
        slider.setVisibility(View.VISIBLE);
        tvChangingStatus.setVisibility(View.GONE);
        slider.setProgress(50);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvChangingStatus.setVisibility(View.INVISIBLE);
                ViewUtils.setVisibility(View.VISIBLE, llSlider);
            }
        }, 50);
    }

    @Override
    public void onAnswerCompleted(int position) {
        if ((questionList.size() - 1) > position) {
            QuestionView questionView = getQuestionView(position + 1);
            questionView.requestFocus();
        } else {
            onNextQuestion();
        }
    }

    @Override
    public void onFocusChanged(int position) {
        for (int i = 0; i < NUMBER_OF_QUESTION; i++) {
            QuestionView questionView = getQuestionView(i);
            if (position == i) questionView.requestFocus();
            else questionView.setUI();
        }
    }

    private QuestionView getQuestionView(int position) {
        return (QuestionView) questionList.get(position).getView();
    }

}
