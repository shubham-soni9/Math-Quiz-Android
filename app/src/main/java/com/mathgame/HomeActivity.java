package com.mathgame;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.mathgame.activity.CareerLevelActivity;
import com.mathgame.activity.GameTypeActivity;
import com.mathgame.activity.MathTutorialActivity;
import com.mathgame.activity.SlideAdditionActivity;
import com.mathgame.activity.SudokuHomeActivity;
import com.mathgame.activity.SudokuTutorialActivity;
import com.mathgame.appdata.Codes;
import com.mathgame.appdata.Dependencies;
import com.mathgame.appdata.GameSettings;
import com.mathgame.dialog.OptionsDialog;
import com.mathgame.model.CustomMode;
import com.mathgame.plugin.tictactoe.selection.TTTSelectionActivity;
import com.mathgame.structure.BaseActivity;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setActionbarAnimation();
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_home;
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
        drawerLayout = findViewById(R.id.activity_home_dl_main);
        Utils.setOnClickListener(this, tvAddition, tvSubtraction, tvMultiplication, tvDivision, tvPercentage, tvSquareRoot
                , cvTicTacToe, cvSudoku, cvSlideAddition, findViewById(R.id.tvSliderHome), findViewById(R.id.tvSliderTutorials)
                , findViewById(R.id.tvSliderSettings), findViewById(R.id.tvSliderPolicy), findViewById(R.id.tvSliderShare)
                , findViewById(R.id.tvSliderRate), findViewById(R.id.tvSliderMoreApps), findViewById(R.id.tvSliderReportBug)
                , findViewById(R.id.tvSliderFeedback), findViewById(R.id.tvSliderExit), findViewById(R.id.tvSliderCareer));
    }

    private void setActionbarAnimation() {
        final DrawerLayout drawerLayout = findViewById(R.id.activity_home_dl_main);
        final CoordinatorLayout content = findViewById(R.id.content_main);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                content.setTranslationX(slideX);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        final MaterialMenuDrawable materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialMenu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        drawerLayout.isDrawerOpen(GravityCompat.START) ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if(newState == DrawerLayout.STATE_IDLE) {
                    if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        materialMenu.setIconState(MaterialMenuDrawable.IconState.ARROW);
                    } else {
                        materialMenu.setIconState(MaterialMenuDrawable.IconState.BURGER);
                    }
                }
            }
        });
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
                Transition.startActivity(this, TTTSelectionActivity.class);
                break;
            case R.id.cvSudoku:
                if (Dependencies.isFirstTimeSudokuLaunch(this)) {
                    Transition.startActivity(this, SudokuTutorialActivity.class);
                } else {
                    Transition.startActivity(this, SudokuHomeActivity.class);
                }
                break;
            case R.id.cvSlideAddition:
                Transition.startActivity(this, SlideAdditionActivity.class);
                break;
            case R.id.tvSliderHome:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.tvSliderTutorials:
                Transition.startActivity(this, MathTutorialActivity.class);
                break;
            case R.id.tvSliderCareer:
                Transition.startActivity(this, CareerLevelActivity.class);
                break;
            case R.id.tvSliderSettings:
                openSettings();
                break;
            case R.id.tvSliderPolicy:
                openPolicyScreen();
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
        }
    }

    private void openSettings() {
    }

    private void openPolicyScreen() {
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
