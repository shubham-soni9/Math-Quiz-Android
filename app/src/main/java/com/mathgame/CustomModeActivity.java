package com.mathgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;


public class CustomModeActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlChallengeType;
    private Spinner        spnCustomFieldValues;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_mode);
        init();
        setData();
    }

    private void setData() {
        String[] dropDownItems = new String[2];
        dropDownItems[0] = getString(R.string.muliple_choice);
        dropDownItems[1] = getString(R.string.yes_no);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dropDownItems);
        spnCustomFieldValues.setAdapter(adapter);
    }

    private void init() {
        rlChallengeType = findViewById(R.id.rlChallengeType);
        spnCustomFieldValues = findViewById(R.id.spnCustomFieldValues);
        Utils.setOnClickListener(this, rlChallengeType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlChallengeType:
                spnCustomFieldValues.performClick();
                break;
        }
    }
}
