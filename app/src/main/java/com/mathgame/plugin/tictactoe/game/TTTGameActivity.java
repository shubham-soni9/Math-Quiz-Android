package com.mathgame.plugin.tictactoe.game;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mathgame.R;
import com.mathgame.appdata.Codes;
import com.mathgame.dialog.OptionsDialog;
import com.mathgame.plugin.tictactoe.TTTConstants;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;

import java.util.Random;

/**
 * The game of Tic Tac Toe is played in this activity.
 * It contains the {@link BoardView} and makes appropriate calls to {@link Brain}.
 */

public class TTTGameActivity extends AppCompatActivity implements BoardView.OnBoardInteractionListener, Brain.OnProcessCompleteListener, View.OnClickListener {
    private static final int BRAIN_RESPONSE_MANUAL_DELAY = 0;

    private Brain brain;

    private BoardView            board;
    private FloatingActionButton resetButton;
    private TextView             turnTextBox;

    private @TTTConstants.Sign
    int player1Sign;
    private @TTTConstants.Sign
    int player2Sign;
    private @TTTConstants.Player
    int turn;
    private @TTTConstants.GameMode
    int gameMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttt_game);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.tic_tac_toe);
        AppCompatImageView ivBack = findViewById(R.id.ivBack);
        Utils.setOnClickListener(this, ivBack);
        initializeViews();
        setClickListeners();

        brain = Brain.getInstance();
        brain.setOnProcessCompleteListener(this);

        getValues();
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        setGameScreen();
        makeFirstMove();
    }

    private void initializeViews() {
        board = findViewById(R.id.board);
        resetButton = findViewById(R.id.reset_button);
        turnTextBox = findViewById(R.id.turn_text_box);
    }

    private void setClickListeners() {
        resetButton.setOnClickListener(this);
        board.setOnBoardInteractionListener(this);
    }

    @SuppressWarnings("WrongConstant")
    private void getValues() {
        Intent intent = getIntent();

        gameMode = intent.getIntExtra("GAME_MODE", TTTConstants.MULTI_PLAYER);
        player1Sign = intent.getIntExtra("PLAYER_1_SIGN", TTTConstants.CIRCLE);
        player2Sign = intent.getIntExtra("PLAYER_2_SIGN", TTTConstants.CROSS);
        turn = intent.getIntExtra("FIRST_TURN", TTTConstants.PLAYER_1);
    }

    /**
     * Sets the game screen according to {@link com.mathgame.plugin.tictactoe.TTTConstants.GameMode} and resets the
     * instance of {@link Brain}.
     */

    private void setGameScreen() {
        switch (gameMode) {
            case TTTConstants.SINGLE_PLAYER:
                brain.setComputerSign(player2Sign);
                turnTextBox.setText(R.string.player_turn_prompt);
                break;
            case TTTConstants.MULTI_PLAYER:
                turnTextBox.setText(R.string.player_1_turn_prompt);
                break;
        }
        brain.reset();
    }

    /**
     * Make the first move if the {@link com.mathgame.plugin.tictactoe.TTTConstants.GameMode} is SINGLE_PLAYER and
     * turn is PLAYER_2.
     * <p>
     * PLAYER_2 will always be the computer if the game mode is SINGLE_PLAYER.
     */

    private void makeFirstMove() {
        if ((gameMode == TTTConstants.SINGLE_PLAYER) && (turn == TTTConstants.PLAYER_2)) {
            int row = generateRandomIndex(), column = generateRandomIndex();
            putSign(getCurrentPlayerSign(), row, column);
            toggleTurn();
            turnTextBox.setText(R.string.player_turn_prompt);
        }
    }

    /**
     * Generates a random index for row or column.
     *
     * @return A random number >= 0 and <= 2.
     */

    private int generateRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(3);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reset_button) {
            board.setEnabled(false);
            board.resetBoard();
        } else if (v.getId() == R.id.ivBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        new OptionsDialog.Builder(this).message(R.string.do_you_want_to_leave_the_game).listener(new OptionsDialog.Listener() {
            @Override
            public void performPositiveAction() {
                Transition.exit(TTTGameActivity.this);
            }

            @Override
            public void performNegativeAction() {

            }
        }).build().show();
    }

    /**
     * Shows a {@link Snackbar} for board reset.
     */

    private void showBoardResetSnackBar() {
        Utils.snackBar(this, getString(R.string.board_reset), findViewById(R.id.reset_button), Codes.SnackBarType.SUCCESS);
    }

    @Override
    public void onBoardClick(BoardView board, int row, int column) {
        if (board.isAlreadyAdded(row, column)) {
            return;
        }

        putSign(getCurrentPlayerSign(), row, column);
    }

    @Override
    public void onSignAdded(@TTTConstants.Sign int sign) {
        switch (gameMode) {
            case TTTConstants.SINGLE_PLAYER:

                if (sign == player1Sign) {

                    toggleTurn();

                    turnTextBox.setText(R.string.processing_text);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            brain.play();
                        }
                    }, BRAIN_RESPONSE_MANUAL_DELAY);

                    board.setEnabled(false);
                }

                break;

            case TTTConstants.MULTI_PLAYER:

                switch (turn) {
                    case TTTConstants.PLAYER_1:
                        turnTextBox.setText(R.string.player_2_turn_prompt);
                        break;
                    case TTTConstants.PLAYER_2:
                        turnTextBox.setText(R.string.player_1_turn_prompt);
                        break;
                }

                toggleTurn();

                brain.analyzeBoard();

                break;
        }
    }

    @Override
    public void onBoardReset() {
        //noinspection WrongConstant
        turn = getIntent().getIntExtra("FIRST_TURN", TTTConstants.PLAYER_1);

        board.setEnabled(true);

        setGameScreen();
        makeFirstMove();
        showBoardResetSnackBar();
    }

    /**
     * Tells the  of the player whose {@link #turn} is going on.
     *
     * @return The current player's sign.
     */

    private @TTTConstants.Sign
    int getCurrentPlayerSign() {
        return turn == TTTConstants.PLAYER_1 ? player1Sign : player2Sign;
    }

    /**
     * Puts the given  in the given row and column index.
     * <p>
     * Updates {@link Brain} and {@link BoardView}.
     *
     * @param sign   Sign which has to be placed.
     * @param row    Row index of sign.
     * @param column Column index of sign.
     */

    private void putSign(@TTTConstants.Sign int sign, int row, int column) {
        brain.updateBoard(sign, row, column);
        board.addSignToBoard(sign, row, column);
    }

    /**
     * Toggles the {@link #turn}.
     */

    private void toggleTurn() {
        turn = turn == TTTConstants.PLAYER_1 ? TTTConstants.PLAYER_2 : TTTConstants.PLAYER_1;
    }

    @Override
    public void onNextMoveCalculated(int row, int column) {
        putSign(player2Sign, row, column);
        turnTextBox.setText(R.string.player_turn_prompt);
        toggleTurn();
        board.setEnabled(true);
        brain.analyzeBoard();
    }

    @Override
    public void onGameWin(@TTTConstants.Sign int sign, @TTTConstants.WinLinePosition int winLinePosition) {
        board.showWinLine(winLinePosition);

        turnTextBox.setText("");

        if (sign == player1Sign) {
            Toast.makeText(TTTGameActivity.this, "Player 1 Wins", Toast.LENGTH_SHORT).show();
        } else if (sign == player2Sign) {
            switch (gameMode) {
                case TTTConstants.SINGLE_PLAYER:
                    Toast.makeText(TTTGameActivity.this, "Computer Wins", Toast.LENGTH_SHORT).show();
                    break;
                case TTTConstants.MULTI_PLAYER:
                    Toast.makeText(TTTGameActivity.this, "Player 2 Wins", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        board.setEnabled(false);
    }

    @Override
    public void onGameDraw() {
        turnTextBox.setText("");
        Toast.makeText(TTTGameActivity.this, "Draw", Toast.LENGTH_SHORT).show();
        board.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        brain.destroy();
        brain = null;
        super.onDestroy();
    }
}