package com.mathgame.plugin.tictactoe.game;

import android.support.annotation.IntDef;

import com.mathgame.plugin.tictactoe.TTTConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * This class is responsible for playing the game of Tic Tac Toe on behalf of the computer and
 * determining the result of the game.
 */

class Brain {
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL   = 1;
    private static final int DIAGONAL   = 2;
    private static Brain INSTANCE;
    private @TTTConstants.Sign
    final
    int[][] board = new int[3][3];
    private int rowOfResult;
    private int columnOfResult;
    private int depth;
    private @TTTConstants.Sign
    int computerSign;
    private @TTTConstants.Sign
    int playerSign;
    private       OnProcessCompleteListener onProcessCompleteListener;
    // References used by isWin function.
    private final int[]                     winSequence = new int[3];
    private final int[]                     row         = new int[3];
    private final int[]                     column      = new int[3];
    private final int[]                     diagonal1   = new int[3];
    private final int[]                     diagonal2   = new int[3];
    private Brain() {

    }

    /**
     * This method ensures that only one instance of {@link Brain} exists.
     *
     * @return Instance of this class.
     */

    static Brain getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Brain();
        }
        return INSTANCE;
    }

    /**
     * This method provides the row and column indices of the computer's move calculated by
     * {@link #calculateNextMove(int, int)} through {@link OnProcessCompleteListener}.
     */

    void play() {
        if (onProcessCompleteListener == null) {
            return;
        }

        calculateNextMove(computerSign, depth);

        onProcessCompleteListener.onNextMoveCalculated(rowOfResult, columnOfResult);
    }

    /**
     * This method recursively calculates the next move of the computer.
     *
     * @param sign  of computer.
     * @param depth Total number of moves made by both players till now.
     * @return Score for a given move. Used internally by the method to calculate the best move.
     */

    private int calculateNextMove(@TTTConstants.Sign int sign, int depth) {

        if (isWin(computerSign, false)) {
            return 10 - depth;
        } else if (isWin(playerSign, false)) {
            return depth - 10;
        }

        if (depth >= 9) {
            return 0;
        }

        List<Integer> scores = new ArrayList<>(), rowIndices = new ArrayList<>(), columnIndices = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == TTTConstants.EMPTY) {
                    board[i][j] = sign;
                    scores.add(calculateNextMove(getOppositeSign(sign), depth + 1));
                    rowIndices.add(i);
                    columnIndices.add(j);
                    board[i][j] = TTTConstants.EMPTY;
                }
            }
        }

        if (sign == computerSign) {
            int maxScore = -100;
            for (int i = 0; i < scores.size(); i++) {
                if (scores.get(i) > maxScore) {
                    maxScore = scores.get(i);
                }
            }
            return randomizeScore(maxScore, scores, rowIndices, columnIndices);

        } else {
            int minScore = 100;
            for (int i = 0; i < scores.size(); i++) {
                if (scores.get(i) < minScore) {
                    minScore = scores.get(i);
                }
            }
            return randomizeScore(minScore, scores, rowIndices, columnIndices);
        }
    }

    /**
     * Randomly selects a move from moves having score matching the given score.
     *
     * @param score         Score to be matched.
     * @param scores        List of scores.
     * @param rowIndices    List of indices of rows corresponding to scores list.
     * @param columnIndices List of indices of columns corresponding to scores list.
     * @return Randomized score from scores matching the given score.
     */

    private int randomizeScore(int score, List<Integer> scores, List<Integer> rowIndices, List<Integer> columnIndices) {
        List<Integer> equalScoreIndices = new ArrayList<>();

        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) == score) {
                equalScoreIndices.add(i);
            }
        }

        Random rand = new Random();
        int randomIndex = equalScoreIndices.get(rand.nextInt(equalScoreIndices.size()));

        rowOfResult = rowIndices.get(randomIndex);
        columnOfResult = columnIndices.get(randomIndex);

        return score;
    }

    /**
     * Determines whether the given  won the game or not.
     *
     * @param sign             The sign to be checked for the win.
     * @param notifyWinEnabled Flag to enable notification of the win through
     *                         {@link #notifyWin(int, int, int)}.
     * @return true if the given sign won, else false.
     */

    private boolean isWin(@TTTConstants.Sign int sign, boolean notifyWinEnabled) {
        for (int i = 0; i < 3; i++) {
            winSequence[i] = sign;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (i == j) {
                    diagonal1[i] = board[i][j];
                }
                if ((i + j) == 2) {
                    diagonal2[i] = board[i][j];
                }

                row[j] = board[i][j];
                column[j] = board[j][i];
            }

            if (isEqual(row, winSequence)) {
                if (notifyWinEnabled) {
                    notifyWin(sign, HORIZONTAL, i + 1);
                }
                return true;
            } else if (isEqual(column, winSequence)) {
                if (notifyWinEnabled) {
                    notifyWin(sign, VERTICAL, i + 1);
                }
                return true;
            }
        }

        if (isEqual(diagonal1, winSequence)) {
            if (notifyWinEnabled) {
                notifyWin(sign, DIAGONAL, 1);
            }
            return true;
        } else if (isEqual(diagonal2, winSequence)) {
            if (notifyWinEnabled) {
                notifyWin(sign, DIAGONAL, 2);
            }
            return true;
        }

        return false;
    }

    /**
     * Determines whether the given int arrays contain equal values.
     *
     * @param x int array.
     * @param y int array.
     * @return true if equal, else false.
     */

    private boolean isEqual(int[] x, int[] y) {
        for (int i = 0; i < 3; i++) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Analyzes the board for win of either players or draw.
     */

    void analyzeBoard() {
        if (onProcessCompleteListener == null) {
            return;
        }

        if ((!isWin(TTTConstants.CIRCLE, true)) && (!isWin(TTTConstants.CROSS, true)) && (depth >= 9)) {
            onProcessCompleteListener.onGameDraw();
        }
    }

    /**
     * Notify win of the given  through {@link OnProcessCompleteListener}.
     *
     * @param sign      The sign who has won.
     * @param direction One of ROW, COLUMN OR DIAGONAL.
     * @param index     Index of either ROW, COLUMN OR DIAGONAL.
     */

    private void notifyWin(@TTTConstants.Sign int sign, @DirectionOfWinLine int direction, int index) {
        if (onProcessCompleteListener == null) {
            return;
        }

        @TTTConstants.WinLinePosition int winLinePosition = TTTConstants.NONE;

        switch (direction) {
            case HORIZONTAL:
                switch (index) {
                    case 1:
                        winLinePosition = TTTConstants.ROW_1;
                        break;
                    case 2:
                        winLinePosition = TTTConstants.ROW_2;
                        break;
                    case 3:
                        winLinePosition = TTTConstants.ROW_3;
                        break;
                }
                break;
            case VERTICAL:
                switch (index) {
                    case 1:
                        winLinePosition = TTTConstants.COLUMN_1;
                        break;
                    case 2:
                        winLinePosition = TTTConstants.COLUMN_2;
                        break;
                    case 3:
                        winLinePosition = TTTConstants.COLUMN_3;
                        break;
                }
                break;
            case DIAGONAL:
                switch (index) {
                    case 1:
                        winLinePosition = TTTConstants.DIAGONAL_1;
                        break;
                    case 2:
                        winLinePosition = TTTConstants.DIAGONAL_2;
                        break;
                }
                break;
        }

        onProcessCompleteListener.onGameWin(sign, winLinePosition);
    }

    /**
     * Resets the board.
     */

    void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = TTTConstants.EMPTY;
            }
        }
        depth = 0;
    }

    void setComputerSign(int computerSign) {
        this.computerSign = computerSign;
        playerSign = getOppositeSign(computerSign);
    }

    /**
     * Update the board with the given , row index and column
     * index.
     *
     * @param sign   Sign to be placed.
     * @param row    Row index of sign.
     * @param column Column index of sign.
     */

    void updateBoard(@TTTConstants.Sign int sign, int row, int column) {
        board[row][column] = sign;
        depth++;
    }

    private @TTTConstants.Sign
    int getOppositeSign(@TTTConstants.Sign int sign) {
        return sign == TTTConstants.CIRCLE ? TTTConstants.CROSS : TTTConstants.CIRCLE;
    }

    void setOnProcessCompleteListener(OnProcessCompleteListener onProcessCompleteListener) {
        this.onProcessCompleteListener = onProcessCompleteListener;
    }

    /**
     * Destroys the singleton instance of this class.
     * <p>
     * To be called when the scope of this instance is intended to end.
     */

    void destroy() {
        INSTANCE = null;
    }

    @IntDef({HORIZONTAL, VERTICAL, DIAGONAL})
    private @interface DirectionOfWinLine {

    }

    interface OnProcessCompleteListener {

        void onNextMoveCalculated(int row, int column);

        void onGameWin(@TTTConstants.Sign int sign, @TTTConstants.WinLinePosition int winLinePosition);

        void onGameDraw();
    }
}