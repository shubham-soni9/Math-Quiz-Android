package com.mathgame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mathgame.R;
import com.mathgame.model.CustomMode;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;

public class GameTypeActivity extends BaseActivity implements View.OnClickListener {
    private CustomMode customMode;

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
