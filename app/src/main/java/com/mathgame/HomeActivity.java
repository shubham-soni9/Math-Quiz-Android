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
    private TextView tvSubtraction;
    private TextView tvMultiplication;
    private TextView tvDivision;
    private TextView tvSquareRoot;
    private TextView tvPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tvAddition = findViewById(R.id.tvAddition);
        tvSubtraction=findViewById(R.id.tvSubtraction);
        tvMultiplication=findViewById(R.id.tvMultiplication);
        tvDivision=findViewById(R.id.tvDivision);
        tvPercentage=findViewById(R.id.tvPercentage);
        tvSquareRoot=findViewById(R.id.tvSquareRoot);
        Utils.setOnClickListener(this, tvAddition,tvSubtraction,tvMultiplication,tvDivision,tvPercentage,tvSquareRoot);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.tvAddition:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getAdditionGame());
                Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_SINGLE_GAME_ACTIVITY, bundle);
                break;
            case R.id.tvSubtraction:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getSubtraction());
                Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_SINGLE_GAME_ACTIVITY, bundle);
                break;
            case R.id.tvMultiplication:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getMutltiplication());
                Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_SINGLE_GAME_ACTIVITY, bundle);
                break;
            case R.id.tvDivision:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getDivision());
                Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_SINGLE_GAME_ACTIVITY, bundle);
                break;
            case R.id.tvSquareRoot:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getSquareRoot());
                Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_SINGLE_GAME_ACTIVITY, bundle);
                break;
            case R.id.tvPercentage:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getPercentage());
                Transition.transitForResult(this, SingleGameActivity.class, Codes.RequestCode.OPEN_SINGLE_GAME_ACTIVITY, bundle);
                break;
        }
    }
}
