package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.mathgame.R;
import com.mathgame.plugin.numberpicker.NumberPicker;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.RadioButton;

public class AddCustomModeActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout     rlChallengeType;
    private Spinner            spnCustomFieldValues;
    private AppCompatImageView ivBack;
    private AppCompatTextView  tvTitle;
    private NumberPicker       npNumberOfQuestion;
    private NumberPicker       npNumberOfVariables;
    private NumberPicker npNumberOfSkip;
    private RadioButton        rbSinglePlayer;
    private RadioButton        rbDualPlayer;
    private RadioButton        rbTimerNone;
    private RadioButton        rbTimerPerTest;
    private RadioButton        rbTimerPerQuestion;
    private CheckBox           cbAddition;
    private CheckBox           cbSubtraction;
    private CheckBox           cbMultiplication;
    private CheckBox           cbDivision;
    private CheckBox           cbPercentage;
    private CheckBox           cbSquareRoot;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_mode);
        init();
        setData();
    }

    private void setData() {
        String[] dropDownItems = new String[3];
        dropDownItems[0] = getString(R.string.muliple_choice);
        dropDownItems[1] = getString(R.string.yes_no);
        dropDownItems[2] = getString(R.string.manual_input);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dropDownItems);
        spnCustomFieldValues.setAdapter(adapter);
    }

    private void init() {
        rlChallengeType = findViewById(R.id.rlChallengeType);
        spnCustomFieldValues = findViewById(R.id.spnCustomFieldValues);
        tvTitle = findViewById(R.id.tvTitle);
        npNumberOfQuestion = findViewById(R.id.npNumberOfQuestion);
        ivBack = findViewById(R.id.ivBack);
        rbSinglePlayer = findViewById(R.id.rbSinglePlayer);
        rbDualPlayer = findViewById(R.id.rbDualPlayer);
        cbAddition = findViewById(R.id.cbAddition);
        cbSubtraction = findViewById(R.id.cbSubtraction);
        cbMultiplication = findViewById(R.id.cbMultiplication);
        cbDivision = findViewById(R.id.cbDivision);
        cbPercentage = findViewById(R.id.cbPercentage);
        cbSquareRoot = findViewById(R.id.cbSquareRoot);
        npNumberOfVariables = findViewById(R.id.npNumberOfVariables);
        rbTimerNone = findViewById(R.id.rbTimerNone);
        rbTimerPerTest = findViewById(R.id.rbTimerPerTest);
        npNumberOfSkip=findViewById(R.id.npNumberOfSkip);
        rbTimerPerQuestion = findViewById(R.id.rbTimerPerQuestion);
        Utils.setOnClickListener(this, rlChallengeType, ivBack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChallengeType:
                spnCustomFieldValues.performClick();
                break;
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
