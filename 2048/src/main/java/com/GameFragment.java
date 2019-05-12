package com;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.util.Transition;

import com.slidegame.Game;
import com.slidegame.GameView;
import com.slidegame.Tile;
import com.thedarken.myo.R;
import com.tools.InputListener;
import com.tools.KeyListener;
import com.tools.ScoreKeeper;

public class GameFragment extends Fragment implements KeyListener,  Game.GameStateListener {
    private static final String TAG = "2048Myo:GameFragment";
    public static boolean sReversed = false;

    private GameView mGameView;
    private Game mGame;

    private static final String SCORE = "savegame.score";
    private static final String UNDO_SCORE = "savegame.undoscore";
    private static final String CAN_UNDO = "savegame.canundo";
    private static final String UNDO_GRID = "savegame.undo";
    private static final String GAME_STATE = "savegame.gamestate";
    private static final String UNDO_GAME_STATE = "savegame.undogamestate";

    private TextView mScoreText;
    private TextView mHighScoreText;
    private TextView mOverlay;
    private ScoreKeeper mScoreKeeper;
    private ImageButton mResetButton, mUndoButton;
    private TextView              mTitleText;
    private SlideAdditionActivity mSlideAdditionActivity;
    private AppCompatImageView ivBack;

    @Override
    public void onAttach(Activity activity) {
        mSlideAdditionActivity = (SlideAdditionActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_game_layout, container, false);
        mGameView = layout.findViewById(R.id.gameview);
        mScoreText = layout.findViewById(R.id.tv_score);
        mHighScoreText = layout.findViewById(R.id.tv_highscore);
        mTitleText = layout.findViewById(R.id.tv_title);
        mResetButton = layout.findViewById(R.id.ib_reset);
        mUndoButton = layout.findViewById(R.id.ib_undo);
        mOverlay = layout.findViewById(R.id.tv_endgame_overlay);
        ivBack=layout.findViewById(R.id.ivBack);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScoreKeeper = new ScoreKeeper(getActivity());
        mScoreKeeper.setViews(mScoreText, mHighScoreText);
        mGame = new Game(getActivity());
        mGame.setup(mGameView);
        mGame.setScoreListener(mScoreKeeper);
        mGame.setGameStateListener(this);
        mGame.newGame();
        InputListener input = new InputListener();
        input.setView(mGameView);
        input.setGame(mGame);

        mTitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mGame.isEndlessMode()) {
                    mGame.setEndlessMode();
                    mTitleText.setText(Html.fromHtml("&infin;"));
                }
            }
        });
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGame.newGame();
                mTitleText.setText(Html.fromHtml("2048"));
            }
        });
        mResetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), mSlideAdditionActivity.getString(R.string.start_new_game), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGame.revertUndoState();
                if (mGame.getGameState() == Game.State.ENDLESS || mGame.getGameState() == Game.State.ENLESS_WON) {
                    mTitleText.setText(Html.fromHtml("&infin;"));
                } else {
                    mTitleText.setText(Html.fromHtml("2048"));
                }
            }
        });
        mUndoButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), mSlideAdditionActivity.getString(R.string.undo_last_move), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transition.exit(getActivity());
            }
        });
    }


    @Override
    public void onResume() {
        load();
        if (mGame.getGameState() == Game.State.ENDLESS || mGame.getGameState() == Game.State.ENLESS_WON) {
            mTitleText.setText(Html.fromHtml("&infin;"));
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mGame != null)
            save();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            mGame.move(Game.DIRECTION_DOWN);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            mGame.move(Game.DIRECTION_UP);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            mGame.move(Game.DIRECTION_LEFT);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            mGame.move(Game.DIRECTION_RIGHT);
            return true;
        }
        return false;
    }

    private void save() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = settings.edit();
        Tile[][] field = mGame.getGameGrid().getGrid();
        Tile[][] undoField = mGame.getGameGrid().getUndoGrid();
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] != null) {
                    editor.putInt(xx + " " + yy, field[xx][yy].getValue());
                } else {
                    editor.putInt(xx + " " + yy, 0);
                }

                if (undoField[xx][yy] != null) {
                    editor.putInt(UNDO_GRID + xx + " " + yy, undoField[xx][yy].getValue());
                } else {
                    editor.putInt(UNDO_GRID + xx + " " + yy, 0);
                }
            }
        }
        editor.putLong(SCORE, mGame.getScore());
        editor.putLong(UNDO_SCORE, mGame.getLastScore());
        editor.putBoolean(CAN_UNDO, mGame.isCanUndo());
        editor.putString(GAME_STATE, mGame.getGameState().name());
        editor.putString(UNDO_GAME_STATE, mGame.getLastGameState().name());
        editor.commit();
    }

    private void load() {
        //Stopping all animations
        mGameView.cancelAnimations();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        for (int xx = 0; xx < mGame.getGameGrid().getGrid().length; xx++) {
            for (int yy = 0; yy < mGame.getGameGrid().getGrid()[0].length; yy++) {
                int value = settings.getInt(xx + " " + yy, -1);
                if (value > 0) {
                    mGame.getGameGrid().getGrid()[xx][yy] = new Tile(xx, yy, value);
                } else if (value == 0) {
                    mGame.getGameGrid().getGrid()[xx][yy] = null;
                }

                int undoValue = settings.getInt(UNDO_GRID + xx + " " + yy, -1);
                if (undoValue > 0) {
                    mGame.getGameGrid().getUndoGrid()[xx][yy] = new Tile(xx, yy, undoValue);
                } else if (value == 0) {
                    mGame.getGameGrid().getUndoGrid()[xx][yy] = null;
                }
            }
        }

        mGame.setScore(settings.getLong(SCORE, 0));
        mGame.setLastScore(settings.getLong(UNDO_SCORE, 0));
        mGame.setCanUndo(settings.getBoolean(CAN_UNDO, mGame.isCanUndo()));
        try {
            mGame.updateGameState(Game.State.valueOf(settings.getString(GAME_STATE, Game.State.NORMAL.name())));
        } catch (IllegalArgumentException e) {
            mGame.updateGameState(Game.State.NORMAL);
        }
        try {
            mGame.setLastGameState(Game.State.valueOf(settings.getString(UNDO_GAME_STATE, Game.State.NORMAL.name())));
        } catch (IllegalArgumentException e) {
            mGame.setLastGameState(Game.State.NORMAL);
        }
        mGame.updateUI();
    }

    @Override
    public void onGameStateChanged(Game.State state) {
        if (state == Game.State.WON || state == Game.State.ENLESS_WON) {
            mOverlay.setVisibility(View.VISIBLE);
            mOverlay.setText(R.string.you_win);
        } else if (state == Game.State.LOST) {
            mOverlay.setVisibility(View.VISIBLE);
            mOverlay.setText(R.string.game_over);
        } else {
            mOverlay.setVisibility(View.GONE);
        }
    }
}
