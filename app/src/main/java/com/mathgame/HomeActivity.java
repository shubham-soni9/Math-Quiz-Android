package com.mathgame;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.mathgame.activity.GameTypeActivity;
import com.mathgame.appdata.Codes;
import com.mathgame.appdata.GameSettings;
import com.mathgame.model.CustomMode;
import com.mathgame.plugin.tictactoe.selection.SelectionActivity;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;

import org.secuso.ui.SplashActivity;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvAddition;
    private TextView tvSubtraction;
    private TextView tvMultiplication;
    private TextView tvDivision;
    private TextView tvSquareRoot;
    private TextView tvPercentage;
    private CardView cvTicTacToe;
    private CardView cvSudoku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        tvAddition = findViewById(R.id.tvAddition);
        tvSubtraction = findViewById(R.id.tvSubtraction);
        tvMultiplication = findViewById(R.id.tvMultiplication);
        tvDivision = findViewById(R.id.tvDivision);
        tvPercentage = findViewById(R.id.tvPercentage);
        tvSquareRoot = findViewById(R.id.tvSquareRoot);
        cvTicTacToe = findViewById(R.id.cvTicTacToe);
        cvSudoku = findViewById(R.id.cvSudoku);
        Utils.setOnClickListener(this, tvAddition, tvSubtraction, tvMultiplication, tvDivision, tvPercentage, tvSquareRoot
                , cvTicTacToe, cvSudoku);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.tvAddition:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getAdditionGame());
                Transition.transitForResult(this, GameTypeActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
                break;
            case R.id.tvSubtraction:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getSubtraction());
                Transition.transitForResult(this, GameTypeActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
                break;
            case R.id.tvMultiplication:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getMultiplication());
                Transition.transitForResult(this, GameTypeActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
                break;
            case R.id.tvDivision:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getDivision());
                Transition.transitForResult(this, GameTypeActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
                break;
            case R.id.tvSquareRoot:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getSquareRoot());
                Transition.transitForResult(this, GameTypeActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
                break;
            case R.id.tvPercentage:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getPercentage());
                Transition.transitForResult(this, GameTypeActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
                break;
            case R.id.cvTicTacToe:
                Transition.startActivity(this, SelectionActivity.class);
                break;
            case R.id.cvSudoku:
                Transition.startActivity(this, SplashActivity.class);
                break;
        }
    }
}
