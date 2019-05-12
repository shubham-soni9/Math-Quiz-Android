package com.mathgame;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.mathgame.activity.GameTypeActivity;
import com.mathgame.appdata.Codes;
import com.mathgame.appdata.GameSettings;
import com.mathgame.dialog.OptionsDialog;
import com.mathgame.model.CustomMode;
import com.mathgame.plugin.slidegame.SlideAdditionActivity;
import com.mathgame.plugin.sudoku.ui.SudokuMainActivity;
import com.mathgame.plugin.tictactoe.selection.SelectionActivity;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        TextView tvAddition = findViewById(R.id.tvAddition);
        TextView tvSubtraction = findViewById(R.id.tvSubtraction);
        TextView tvMultiplication = findViewById(R.id.tvMultiplication);
        TextView tvDivision = findViewById(R.id.tvDivision);
        TextView tvPercentage = findViewById(R.id.tvPercentage);
        TextView tvSquareRoot = findViewById(R.id.tvSquareRoot);
        CardView cvTicTacToe = findViewById(R.id.cvTicTacToe);
        CardView cvSudoku = findViewById(R.id.cvSudoku);
        CardView cvSlideAddition = findViewById(R.id.cvSlideAddition);
        AppCompatImageView ivHome = findViewById(R.id.ivHome);
        drawerLayout = findViewById(R.id.activity_home_dl_main);
        AppCompatImageView ivSelectedColor = findViewById(R.id.ivSelectedColor);

        Utils.setOnClickListener(this, tvAddition, tvSubtraction, tvMultiplication, tvDivision, tvPercentage, tvSquareRoot
                , cvTicTacToe, cvSudoku, cvSlideAddition, ivHome, findViewById(R.id.tvSliderHome), findViewById(R.id.tvSliderTutorials)
                , findViewById(R.id.tvSliderSettings), findViewById(R.id.tvSliderPolicy), findViewById(R.id.tvSliderShare)
                , findViewById(R.id.tvSliderRate), findViewById(R.id.tvSliderMoreApps), findViewById(R.id.tvSliderReportBug)
                , findViewById(R.id.tvSliderFeedback), findViewById(R.id.tvSliderExit), findViewById(R.id.rlSliderTheme));
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
            case R.id.tvPercentage:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getPercentage());
                Transition.transitForResult(this, GameTypeActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
                break;
            case R.id.tvSquareRoot:
                bundle.putParcelable(CustomMode.class.getName(), GameSettings.getSquareRoot());
                Transition.transitForResult(this, GameTypeActivity.class, Codes.RequestCode.OPEN_GAME_TYPE_ACTIVITY, bundle);
                break;
            case R.id.cvTicTacToe:
                Transition.startActivity(this, SelectionActivity.class);
                break;
            case R.id.cvSudoku:
                Transition.startActivity(this, SudokuMainActivity.class);
                break;
            case R.id.cvSlideAddition:
                Transition.startActivity(this, SlideAdditionActivity.class);
                break;
            case R.id.ivHome:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.tvSliderHome:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.tvSliderTutorials:
                break;
            case R.id.tvSliderSettings:
                break;
            case R.id.tvSliderPolicy:
                break;
            case R.id.tvSliderShare:
                shareApp();
                break;
            case R.id.tvSliderRate:
                sendRating();
                break;
            case R.id.tvSliderMoreApps:
                sendMoreApps();
                break;
            case R.id.tvSliderReportBug:
                sendBugReport();
                break;
            case R.id.tvSliderFeedback:
                sendFeedback();
                break;
            case R.id.tvSliderExit:
                exit();
                break;
            case R.id.rlSliderTheme:
                changeTheme();
                break;
        }
    }

    private void changeTheme() {
    }

    private void shareApp() {
    }

    private void sendRating() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                                    | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                e.printStackTrace();
                startActivity(new Intent(Intent.ACTION_VIEW,
                                         Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            } catch (Exception e2) {
                e2.printStackTrace();
                Utils.snackBar(this, R.string.some_error_occurred);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.snackBar(this, R.string.some_error_occurred);
        }
    }

    private void sendMoreApps() {
        Utils.openOtherApps(this);
    }

    private void sendFeedback() {
        String subject = getString(R.string.feedback) + " : " + getString(R.string.app_name);
        String message = getString(R.string.feedback) + " : ";
        Utils.openEmailApp(this, subject, message);
    }

    private void sendBugReport() {
        String subject = getString(R.string.report_a_bug) + " : " + getString(R.string.app_name);
        String message = getString(R.string.report_a_bug) + " : ";
        Utils.openEmailApp(this, subject, message);
    }


    private void exit() {
        new OptionsDialog.Builder(this)
                .message(R.string.are_you_sure_you_want_to_exit)
                .listener(new OptionsDialog.Listener() {
                    @Override
                    public void performPositiveAction() {
                        finishAffinity();
                    }

                    @Override
                    public void performNegativeAction() {
                    }
                }).build().show();
    }
}
