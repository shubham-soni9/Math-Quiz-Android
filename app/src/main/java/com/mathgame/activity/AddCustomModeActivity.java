package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.database.ObjectBox;
import com.mathgame.model.CustomMode;
import com.mathgame.plugin.MaterialEditText;
import com.mathgame.plugin.numberpicker.NumberPicker;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Codes;
import com.mathgame.util.Constant;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.RadioButton;

import io.objectbox.Box;

public class AddCustomModeActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
        , NumberPicker.OnValueChangeListener {
    private RelativeLayout     rlChallengeType;
    private Spinner            spnCustomFieldValues;
    private AppCompatImageView ivBack;
    private MaterialEditText   metTitle;
    private NumberPicker       npNumberOfQuestion;
    private NumberPicker       npNumberOfVariables;
    private NumberPicker       npNumberOfSkip;
    private NumberPicker       npTimerMinuteValue;
    private NumberPicker       npTimerSecondValue;
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
    private Button             btnSaveSettings;
    private TextView           tvSelectedChallenge;
    private int                gameType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_mode);
        init();
        setData();
        setListener();
    }

    private void setListener() {
    }

    private void setData() {
        final String[] dropDownItems = new String[3];
        dropDownItems[0] = getString(R.string.multiple_choice);
        dropDownItems[1] = getString(R.string.yes_no);
        dropDownItems[2] = getString(R.string.manual_input);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dropDownItems);
        spnCustomFieldValues.setAdapter(adapter);
        spnCustomFieldValues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameType = position;
                tvSelectedChallenge.setText(dropDownItems[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnCustomFieldValues.setSelection(0);
        npNumberOfSkip.setMaxValue(1);
        npNumberOfQuestion.setOnValueChangedListener(this);
    }

    private void init() {
        rlChallengeType = findViewById(R.id.rlChallengeType);
        spnCustomFieldValues = findViewById(R.id.spnCustomFieldValues);
        metTitle = findViewById(R.id.metTitle);
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
        npTimerMinuteValue = findViewById(R.id.npTimerMinuteValue);
        npTimerSecondValue = findViewById(R.id.npTimerSecondValue);
        rbTimerNone = findViewById(R.id.rbTimerNone);
        rbTimerPerTest = findViewById(R.id.rbTimerPerTest);
        npNumberOfSkip = findViewById(R.id.npNumberOfSkip);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        rbTimerPerQuestion = findViewById(R.id.rbTimerPerQuestion);
        tvSelectedChallenge = findViewById(R.id.tvSelectedChallenge);
        Utils.setOnClickListener(this, rlChallengeType, ivBack, btnSaveSettings);
        Utils.setOnCheckChangedListener(this, rbSinglePlayer, rbDualPlayer, rbTimerNone, rbTimerPerQuestion, rbTimerPerTest
                , cbSquareRoot);
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
            case R.id.btnSaveSettings:
                if (validate()) {
                    saveSettings();
                }
                break;
        }
    }

    private boolean validate() {
        if (Utils.isEmpty(metTitle)) {
            Utils.snackBar(this, R.string.please_enter_title);
            return false;
        }
        if (!Utils.isAnyCheckboxChecked(cbAddition, cbSubtraction, cbMultiplication, cbDivision, cbSquareRoot, cbPercentage)) {
            Utils.snackBar(this, R.string.please_at_least_choose_one_operation);
            return false;
        }
        return true;
    }

    private String getMathOperations() {
        String mathOperations = Constant.EMPTY;
        if (cbAddition.isChecked()) mathOperations = mathOperations + "+ ";
        if (cbSubtraction.isChecked()) mathOperations = mathOperations + "- ";
        if (cbMultiplication.isChecked()) mathOperations = mathOperations + "* ";
        if (cbDivision.isChecked()) mathOperations = mathOperations + "/ ";
        if (cbPercentage.isChecked()) mathOperations = mathOperations + "%";
        if (cbSquareRoot.isChecked()) mathOperations = mathOperations + "sqt() ";
        return mathOperations;
    }

    private void saveSettings() {
        CustomMode customMode = new CustomMode();
        customMode.setTitle(Utils.get(metTitle));
        customMode.setMathOperations(getMathOperations());
        customMode.setNumberOfQuestions(npNumberOfQuestion.getValue());
        customMode.setSkipNumbers(npNumberOfSkip.getValue());
        customMode.setNumberOfVariables(npNumberOfVariables.getValue());

        // Define Player Type
        customMode.setPlayerType(rbSinglePlayer.isChecked() ? Codes.PlayerType.SINGLE.value : Codes.PlayerType.DUAL.value);

        // Define Timer Type
        if (rbTimerNone.isChecked()) customMode.setTimerType(Codes.TimerType.NONE.value);
        if (rbTimerPerTest.isChecked()) customMode.setTimerType(Codes.TimerType.PER_TEST.value);
        if (rbTimerPerQuestion.isChecked()) customMode.setTimerType(Codes.TimerType.PER_QUESTION.value);

        //Define Timer Value
        customMode.setTimerValue(npTimerMinuteValue.getValue() * 60 + npTimerSecondValue.getValue());

        //Define Game Type
        customMode.setGameType(gameType);
        // Save Data in objectbox
        Box<CustomMode> userBox = ObjectBox.get().boxFor(CustomMode.class);
        userBox.put(customMode);
        setResult(RESULT_OK);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Transition.exit(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rbSinglePlayer:
                if (isChecked) rbDualPlayer.setChecked(false);
                break;
            case R.id.rbDualPlayer:
                if (isChecked) rbSinglePlayer.setChecked(false);
                break;
            case R.id.rbTimerNone:
                if (isChecked) {
                    rbTimerPerQuestion.setChecked(false);
                    rbTimerPerTest.setChecked(false);
                }
                break;
            case R.id.rbTimerPerQuestion:
                if (isChecked) {
                    rbTimerNone.setChecked(false);
                    rbTimerPerTest.setChecked(false);
                }
                break;
            case R.id.rbTimerPerTest:
                if (isChecked) {
                    rbTimerPerQuestion.setChecked(false);
                    rbTimerNone.setChecked(false);
                }
                break;
            case R.id.cbSquareRoot:
                npNumberOfVariables.setMinValue(isChecked ? 1 : 2);
                break;
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (picker.getId() == R.id.npNumberOfQuestion) {
            npNumberOfSkip.setMaxValue(npNumberOfQuestion.getValue());
        }
    }
}
