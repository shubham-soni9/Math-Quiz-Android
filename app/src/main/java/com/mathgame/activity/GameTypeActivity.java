package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Codes;
import com.mathgame.appdata.Constant;
import com.mathgame.dialog.SettingsDialog;
import com.mathgame.model.CustomMode;
import com.mathgame.structure.BaseActivity;
import com.util.Transition;
import com.mathgame.util.Utils;

public class GameTypeActivity extends BaseActivity implements View.OnClickListener {
    private CustomMode customMode;
    private TextView   tvHeader;
    private CardView   cvLearn, cvPractice, cvTest, cvDual, cvMultiple, cvYesNo;
    private AppCompatImageView ivSettings;
    private TextView tvMultipleQuestions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_type);
        init();
        setData();
    }

    private void init() {
        cvLearn = findViewById(R.id.cvLearn);
        cvPractice = findViewById(R.id.cvPractice);
        cvDual = findViewById(R.id.cvDual);
        cvMultiple = findViewById(R.id.cvMultiple);
        cvTest = findViewById(R.id.cvTest);
        cvYesNo = findViewById(R.id.cvYesNo);
        ivSettings = findViewById(R.id.ivSettings);
        tvMultipleQuestions=findViewById(R.id.tvMultipleQuestions);
        Utils.setOnClickListener(this, findViewById(R.id.ivBack), cvLearn, cvDual, cvMultiple, cvPractice, cvTest, cvYesNo
                , ivSettings);
    }

    private void setData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            customMode = bundle.getParcelable(CustomMode.class.getName());
            setHeader();
        }
    }

    private void setHeader() {
        tvHeader = findViewById(R.id.tvHeader);
        switch (customMode.getMathOperations()) {
            case Constant.MathSign.ADDITION:
                tvHeader.setText(R.string.addition);
                break;
            case Constant.MathSign.SUBTRACTION:
                tvHeader.setText(R.string.subtraction);
                break;
            case Constant.MathSign.MULTIPLICATION:
                tvHeader.setText(R.string.multiplication);
                break;
            case Constant.MathSign.DIVISION:
                tvHeader.setText(R.string.division);
                break;
            case Constant.MathSign.PERCENTAGE:
                tvHeader.setText(R.string.percentage);
                break;
            case Constant.MathSign.SQUARE_ROOT:
                tvHeader.setText(R.string.square_root);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.cvLearn:
                startLearning();
                break;
            case R.id.cvPractice:
                startPractice();
                break;
            case R.id.cvTest:
                startTest();
                break;
            case R.id.cvDual:
                startDual();
                break;
            case R.id.cvMultiple:
                startMultiple();
                break;
            case R.id.cvYesNo:
                startYesNo();
                break;
            case R.id.ivSettings:
                openSettingsDialog();
                break;
        }
    }

    private void openSettingsDialog() {
        new SettingsDialog.Builder(this)
                .customMode(customMode)
                .listener(new SettingsDialog.Listener() {
                    @Override
                    public void performPositiveAction(int purpose, CustomMode backpack) {

                    }

                    @Override
                    public void performNegativeAction(int purpose, Bundle backpack) {

                    }
                }).build().show();
    }


    private void startYesNo() {
        customMode.setGameType(Codes.GameType.YES_NO.value);
        Bundle bundle = new Bundle();
        bundle.putParcelable(CustomMode.class.getName(), customMode);
        Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
    }

    private void startTest() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CustomMode.class.getName(), customMode);
        Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
    }

    private void startPractice() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CustomMode.class.getName(), customMode);
        Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
    }

    private void startLearning() {
    }

    private void startDual() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CustomMode.class.getName(), customMode);
        Transition.transitForResult(this, DualGameActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
    }

    private void startMultiple() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CustomMode.class.getName(), customMode);
        Transition.transitForResult(this, MultipleQuestionActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
    }

    @Override
    public void onBackPressed() {
        Transition.exit(this);
    }
}
