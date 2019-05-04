package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.appdata.Constant;
import com.mathgame.model.CustomMode;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;

public class GameTypeActivity extends BaseActivity implements View.OnClickListener {
    private CustomMode customMode;
    private TextView   tvHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_type);
        init();
        setData();
    }

    private void setData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            customMode = bundle.getParcelable(CustomMode.class.getName());
            setHeader();
        }
    }

    private void setHeader() {
        tvHeader=findViewById(R.id.tvHeader);
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

    private void init() {
        Utils.setOnClickListener(this, findViewById(R.id.ivBack));
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
