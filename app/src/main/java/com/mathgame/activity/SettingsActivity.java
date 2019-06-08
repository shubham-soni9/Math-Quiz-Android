package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Constant;
import com.mathgame.appdata.Dependencies;
import com.mathgame.dialog.LanguageDialog;
import com.mathgame.dialog.OptionsDialog;
import com.mathgame.model.CustomMode;
import com.mathgame.model.Settings;
import com.mathgame.plugin.numberpicker.NumberPicker;
import com.mathgame.plugin.switchBtn.ToggleSwitch;
import com.mathgame.structure.BaseActivity;
import com.mathgame.structure.BaseApplication;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private TextView     tvChangeLanguage;
    private CustomMode   customMode;
    private ToggleSwitch twDifficultyLevel;
    private NumberPicker npNumberOfQuestion, npNumberOfSkip, npTimerSecondValue;
    private RelativeLayout rlEnableTimer, rlTimerValue;
    private AppCompatCheckBox cbEnableTimer;
    private Settings          settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setData();
    }

    private void init() {
        RelativeLayout llChangeLanguage = findViewById(R.id.llChangeLanguage);
        TextView tvResetSettings = findViewById(R.id.tvResetSettings);
        tvChangeLanguage = findViewById(R.id.tvChangeLanguage);
        twDifficultyLevel = findViewById(R.id.twDifficultyLevel);
        npNumberOfQuestion = findViewById(R.id.npNumberOfQuestion);
        npNumberOfSkip = findViewById(R.id.npNumberOfSkip);
        rlEnableTimer = findViewById(R.id.rlEnableTimer);
        cbEnableTimer = findViewById(R.id.cbEnableTimer);
        rlTimerValue = findViewById(R.id.rlTimerValue);
        npTimerSecondValue = findViewById(R.id.npTimerSecondValue);
        Utils.setOnClickListener(this, llChangeLanguage, tvResetSettings, findViewById(R.id.ivBack));
    }

    private void setData() {
        tvChangeLanguage.setText(Constant.AppLanguage.getLanguageByCode(Dependencies.getLanguageCode(this)).name);
        cbEnableTimer.setOnCheckedChangeListener((buttonView, isChecked) -> rlTimerValue.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        npNumberOfQuestion.setOnValueChangedListener(picker -> npNumberOfSkip.setMaxValue(npNumberOfQuestion.getMaxValue()));
        rlEnableTimer.setOnClickListener(v -> cbEnableTimer.setChecked(!cbEnableTimer.isChecked()));
        settings = Dependencies.getSettings(this);
        customMode = settings.getGeneralMode();
        twDifficultyLevel.setCheckedTogglePosition(customMode.getDifficulty());
        npNumberOfQuestion.setValue(customMode.getNumberOfQuestions());
        npNumberOfSkip.setMaxValue(customMode.getNumberOfQuestions());
        npNumberOfSkip.setValue(customMode.getSkipNumbers());
        cbEnableTimer.setChecked(customMode.getTimerValue() > 0);
        if (customMode.getTimerValue() > 0) {
            npTimerSecondValue.setValue(customMode.getTimerValue());
        }
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llChangeLanguage:
                changeLanguage();
                break;
            case R.id.tvResetSettings:
                resetSettings();
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    private void resetSettings() {
        new OptionsDialog.Builder(this)
                .message(R.string.are_you_sure_you_want_to_reset_the_settings)
                .listener(new OptionsDialog.Listener() {
                    @Override
                    public void performPositiveAction() {
                        BaseApplication.initSettings(SettingsActivity.this, null);
                        setData();
                    }

                    @Override
                    public void performNegativeAction() {
                    }
                }).build().show();
    }

    private void changeLanguage() {
        ArrayList<Constant.AppLanguage> languages = new ArrayList<>();
        Collections.addAll(languages, Constant.AppLanguage.values());
        LanguageDialog
                .with(this)
                .show(getString(R.string.change_language), languages, true,
                      (clickedPosition, code) -> {
                          Utils.setLanguage(this, code);
                          tvChangeLanguage.setText(Constant.AppLanguage.getLanguageByCode(Dependencies.getLanguageCode(this)).name);
                      }, Dependencies.getLanguageCode(this));
    }

    @Override
    public void onBackPressed() {
        customMode.setDifficulty(twDifficultyLevel.getCheckedTogglePosition());
        customMode.setSkipNumbers(npNumberOfSkip.getValue());
        customMode.setNumberOfQuestions(npNumberOfQuestion.getValue());
        customMode.setTimerValue(cbEnableTimer.isChecked() ? npTimerSecondValue.getValue() : 0);
        settings.setGeneralMode(customMode);
        Dependencies.saveSettings(this,settings);
        Transition.exit(this);
    }
}
