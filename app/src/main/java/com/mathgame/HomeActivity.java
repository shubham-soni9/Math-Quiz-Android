package com.mathgame;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mathgame.activity.CustomModeActivity;
import com.mathgame.activity.SingleGameActivity;
import com.mathgame.appdata.Codes;
import com.mathgame.appdata.GameSettings;
import com.mathgame.model.CustomMode;
import com.mathgame.structure.BaseActivity;
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
        tvCustomMode = findViewById(R.id.tvCustomMode);
        Utils.setOnClickListener(this, tvAddition, tvCustomMode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddition:
                Bundle bundle = new Bundle();
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getAdditionGame());
                Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_SINGLE_GAME_ACTIVITY, bundle);
                break;
            case R.id.tvCustomMode:
                Transition.startActivity(this, CustomModeActivity.class);
                break;
        }
    }
}
