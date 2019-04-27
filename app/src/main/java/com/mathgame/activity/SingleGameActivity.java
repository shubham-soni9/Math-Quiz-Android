package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mathgame.R;
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
    private TextView                     tvQuestion;
    private View                         vMultipleChoice;
    private View                         vGameYesOrNo;
    private View                         vInputAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);
        init();
        // pbTimer.animateProgress(8000, 0, 100);
        Utils.setOnClickListener(this, ivBack);
        setData();
    }

    private void setData() {
        customMode = Objects.requireNonNull(getIntent().getExtras()).getParcelable(CustomMode.class.getName());
        Utils.logRequestBody(customMode);
    }

    private void init() {
        pbTimer = findViewById(R.id.pbTimer);
        ivBack = findViewById(R.id.ivBack);
        tvQuestion = findViewById(R.id.tvQuestion);
        vMultipleChoice = findViewById(R.id.vMultipleChoice);
        vGameYesOrNo = findViewById(R.id.vGameYesOrNo);
        vInputAnswer = findViewById(R.id.vInputAnswer);
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
