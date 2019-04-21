package com.mathgame;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvAddition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAddition = findViewById(R.id.tvAddition);
        Utils.setOnClickListener(this, tvAddition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddition:
                Transition.startActivity(this, AdditionActivity.class);
                break;
        }
    }
}
