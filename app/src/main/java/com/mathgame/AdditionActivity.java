package com.mathgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

public class AdditionActivity extends BaseActivity implements View.OnClickListener {
    private RoundedHorizontalProgressBar pbTimer;
    private ImageView                    ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        pbTimer = findViewById(R.id.pbTimer);
        ivBack = findViewById(R.id.ivBack);
        pbTimer.animateProgress(8000, 0, 100);
        Utils.setOnClickListener(this, ivBack);

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
