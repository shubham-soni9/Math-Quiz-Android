package com.mathgame;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mathgame.structure.BaseActivity;
import com.mathgame.activity.AddCustomModeActivity;
import com.mathgame.activity.AdditionActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvAddition;
    private TextView tvCustomMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAddition = findViewById(R.id.tvAddition);
        tvCustomMode=findViewById(R.id.tvCustomMode);
        Utils.setOnClickListener(this, tvAddition, tvCustomMode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddition:
                Transition.startActivity(this, AdditionActivity.class);
                break;
            case R.id.tvCustomMode:
                Transition.startActivity(this, AddCustomModeActivity.class);
                break;
        }
    }
}
