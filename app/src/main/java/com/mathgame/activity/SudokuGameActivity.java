package com.mathgame.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mathgame.R;
import com.mathgame.plugin.sudoku.controller.GameController;
import com.mathgame.plugin.sudoku.controller.GameStateManager;
import com.mathgame.plugin.sudoku.controller.SaveLoadStatistics;
import com.mathgame.plugin.sudoku.controller.Symbol;
import com.mathgame.plugin.sudoku.controller.helper.GameInfoContainer;
import com.mathgame.plugin.sudoku.game.GameDifficulty;
import com.mathgame.plugin.sudoku.game.GameType;
import com.mathgame.plugin.sudoku.game.listener.IGameSolvedListener;
import com.mathgame.plugin.sudoku.game.listener.ITimerListener;
import com.mathgame.plugin.sudoku.ui.listener.IHintDialogFragmentListener;
import com.mathgame.plugin.sudoku.ui.listener.IResetDialogFragmentListener;
import com.mathgame.plugin.sudoku.ui.view.SudokuFieldLayout;
import com.mathgame.plugin.sudoku.ui.view.SudokuKeyboardLayout;
import com.mathgame.plugin.sudoku.ui.view.SudokuSpecialButtonLayout;
import com.mathgame.plugin.sudoku.ui.view.WinDialog;
import com.mathgame.structure.BaseActivity;

import java.util.LinkedList;
import java.util.List;

public class SudokuGameActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IGameSolvedListener, ITimerListener, IHintDialogFragmentListener, IResetDialogFragmentListener {

    private final SaveLoadStatistics        statistics = new SaveLoadStatistics(this);
    private       GameController            gameController;
    private       SudokuFieldLayout         layout;
    private       SudokuKeyboardLayout      keyboard;
    private       SudokuSpecialButtonLayout specialButtonLayout;
    private       TextView                  timerView;
    private       TextView                  viewName;
    private       RatingBar                 ratingBar;
    private       WinDialog                 dialog     = null;
    private       boolean                   gameSolved = false;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (gameSolved) {
            gameController.pauseTimer();
        } else {
            // start the game
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gameController.startTimer();
                }
            }, MAIN_CONTENT_FADEIN_DURATION);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPref.getBoolean("pref_keep_screen_on", true)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        GameType gameType = GameType.Unspecified;
        GameDifficulty gameDifficulty = GameDifficulty.Unspecified;
        int loadLevelID = 0;
        boolean loadLevel = false;

        if (savedInstanceState == null) {

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                gameType = GameType.valueOf(extras.getString("gameType", GameType.Default_9x9.name()));
                gameDifficulty = GameDifficulty.valueOf(extras.getString("gameDifficulty", GameDifficulty.Moderate.name()));
                loadLevel = extras.getBoolean("loadLevel", false);
                if (loadLevel) {
                    loadLevelID = extras.getInt("loadLevelID");
                }
            }

            gameController = new GameController(sharedPref, getApplicationContext());

            List<GameInfoContainer> loadableGames = GameStateManager.getLoadableGameList();

            if (loadLevel && loadableGames.size() > loadLevelID) {
                // load level from GameStateManager
                gameController.loadLevel(loadableGames.get(loadLevelID));
            } else {
                // load a new level
                gameController.loadNewLevel(gameType, gameDifficulty);
            }
        } else {
            gameController = savedInstanceState.getParcelable("gameController");
            // in case we get the same object back
            // because parceling the Object does not always parcel it. Only if needed.
            if (gameController != null) {
                gameController.removeAllListeners();
                gameController.setContextAndSettings(getApplicationContext(), sharedPref);
            } else {
                // Error: no game could be restored. Go back to main menu.
                Intent intent = new Intent(this, SudokuHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
            gameSolved = savedInstanceState.getInt("gameSolved") == 1;
        }


        setContentView(R.layout.activity_game_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.addView();

        if (gameSolved) {
            disableReset();
        }

        //Create new GameField
        layout = findViewById(R.id.sudokuLayout);
        gameController.registerGameSolvedListener(this);
        gameController.registerTimerListener(this);
        statistics.setGameController(gameController);

        layout.setSettingsAndGame(sharedPref, gameController);

        //set KeyBoard
        keyboard = findViewById(R.id.sudokuKeyboardLayout);
        keyboard.removeAllViews();
        keyboard.setGameController(gameController);
        //keyboard.setColumnCount((gameController.getSize() / 2) + 1);
        //keyboard.setRowCount(2);
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);

        // set keyboard orientation
        int orientation = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                LinearLayout.HORIZONTAL : LinearLayout.VERTICAL;

        keyboard.setKeyBoard(gameController.getSize(), orientation);


        //set Special keys
        specialButtonLayout = findViewById(R.id.sudokuSpecialLayout);
        specialButtonLayout.setButtons(gameController, keyboard, getFragmentManager(), orientation);

        //set TimerView
        timerView = findViewById(R.id.timerView);


        //set GameName
        viewName = findViewById(R.id.gameModeText);
        viewName.setText(getString(gameController.getGameType().getStringResID()));

        //set Rating bar
        List<GameDifficulty> difficutyList = GameDifficulty.getValidDifficultyList();
        int numberOfStarts = difficutyList.size();
        ratingBar = findViewById(R.id.gameModeStar);
        ratingBar.setMax(numberOfStarts);
        ratingBar.setNumStars(numberOfStarts);
        ratingBar.setRating(difficutyList.indexOf(gameController.getDifficulty()) + 1);
        ((TextView) findViewById(R.id.difficultyText)).setText(getString(gameController.getDifficulty().getStringResID()));


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (gameSolved) {
            layout.setEnabled(false);
            keyboard.setButtonsEnabled(false);
            specialButtonLayout.setButtonsEnabled(false);
        }

        gameController.notifyHighlightChangedListeners();
        gameController.notifyTimerListener(gameController.getTime());

        // run this so the error list gets build again.
        gameController.onModelChange();

        overridePendingTransition(0, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!gameSolved) {
            gameController.saveGame(this);
        }
        gameController.deleteTimer();
    }

    @Override
    public void onResume() {
        super.onResume();

        View mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
        }

        gameController.initTimer();

        if (!gameSolved) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gameController.startTimer();
                }
            }, MAIN_CONTENT_FADEIN_DURATION);
        }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Symbol s;
        try {
            s = Symbol.valueOf(sharedPref.getString("pref_symbols", Symbol.Default.name()));
        } catch (IllegalArgumentException e) {
            s = Symbol.Default;
        }
        layout.setSymbols(s);
        keyboard.setSymbols(s);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_view, menu);
        return true;
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = null;

        if (id == R.id.menu_reset) {
            ResetConfirmationDialog resetDialog = new ResetConfirmationDialog();
            resetDialog.show(getFragmentManager(), "ResetDialogFragment");
        } else if (id == R.id.nav_newgame) {//create new game
            intent = new Intent(this, SudokuHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
        } else if (id == R.id.menu_settings) {//open settings
            intent = new Intent(this, SudokuSettingsActivity.class);
            intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SudokuSettingsActivity.GamePreferenceFragment.class.getName());
            intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
        } else if (id == R.id.nav_highscore) {// see highscore list
            intent = new Intent(this, StatsActivity.class);
        } else if (id == R.id.menu_help) {//open about page
            intent = new Intent(this, HelpActivity.class);
        }

        if (intent != null) {

            final Intent i = intent;
            // fade out the active activity
            View mainContent = findViewById(R.id.main_content);
            if (mainContent != null) {
                mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(i);
                    overridePendingTransition(0, 0);
                }
            }, NAVDRAWER_LAUNCH_DELAY);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onSolved() {
        gameSolved = true;

        gameController.pauseTimer();
        gameController.deleteGame(this);
        disableReset();

        //Show time hints new plus old best time

        statistics.saveGameStats();

        boolean isNewBestTime = gameController.getUsedHints() == 0
                && statistics.loadStats(gameController.getGameType(), gameController.getDifficulty()).getMinTime() >= gameController.getTime();

        dialog = new WinDialog(this, R.style.WinDialog, timeToString(gameController.getTime()), String.valueOf(gameController.getUsedHints()), isNewBestTime);

        dialog.getWindow().setContentView(R.layout.win_screen_layout);
        //dialog.setContentView(getLayoutInflater().inflate(R.layout.win_screen_layout,null));
        //dialog.setContentView(R.layout.win_screen_layout);
        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        //((TextView)dialog.findViewById(R.id.win_hints)).setText(gameController.getUsedHints());
        //((TextView)dialog.findViewById(R.id.win_time)).setText(timeToString(gameController.getTime()));

        dialog.show();

        final Activity activity = this;
        dialog.findViewById(R.id.win_continue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(activity, SudokuHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                activity.finish();
            }
        });
        dialog.findViewById(R.id.win_showGame_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        layout.setEnabled(false);
        keyboard.setButtonsEnabled(false);
        specialButtonLayout.setButtonsEnabled(false);
    }

    private String timeToString(int time) {
        int seconds = time % 60;
        int minutes = ((time - seconds) / 60) % 60;
        int hours = (time - minutes - seconds) / (3600);
        String h, m, s;
        s = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
        m = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
        h = (hours < 10) ? "0" + hours : String.valueOf(hours);
        return h + ":" + m + ":" + s;
    }


    private void disableReset() {
        NavigationView navView = findViewById(R.id.nav_view);
        Menu navMenu = navView.getMenu();
        navMenu.findItem(R.id.menu_reset).setEnabled(false);
    }

    @Override
    public void onTick(int time) {

        // display the time
        timerView.setText(timeToString(time));

        if (gameSolved) return;
        // save time
        gameController.saveGame(this);
    }

    @Override
    public void onHintDialogPositiveClick() {
        gameController.hint();
    }

    @Override
    public void onResetDialogPositiveClick() {
        gameController.resetLevel();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state

        savedInstanceState.putParcelable("gameController", gameController);
        savedInstanceState.putInt("gameSolved", gameSolved ? 1 : 0);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
    }

    public static class ResetConfirmationDialog extends DialogFragment {

        final LinkedList<IResetDialogFragmentListener> listeners = new LinkedList<>();

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            // Verify that the host activity implements the callback interface
            if (activity instanceof IResetDialogFragmentListener) {
                listeners.add((IResetDialogFragmentListener) activity);
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.reset_confirmation)
                    .setPositiveButton(R.string.reset_confirmation_confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            for (IResetDialogFragmentListener l : listeners) {
                                l.onResetDialogPositiveClick();
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            return builder.create();
        }
    }

}
