package com.mathgame.plugin.sudoku.controller;

import android.util.Log;

import com.mathgame.plugin.sudoku.controller.constant.Action;
import com.mathgame.plugin.sudoku.controller.constant.PrintStyle;
import com.mathgame.plugin.sudoku.controller.constant.QQWing;
import com.mathgame.plugin.sudoku.controller.constant.Symmetry;
import com.mathgame.plugin.sudoku.game.GameBoard;
import com.mathgame.plugin.sudoku.game.GameDifficulty;
import com.mathgame.plugin.sudoku.game.GameType;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Chris on 21.11.2015.
 */
class QQWingController {

    private final QQWingOptions     opts            = new QQWingOptions();
    private final LinkedList<int[]> generated       = new LinkedList<>();
    private       int[]             level;
    private       int[]             solution;
    private       boolean           solveImpossible = false;

    private static long getMicroseconds() {
        return new Date().getTime() * 1000;
    }

    public int[] generate(GameType type, GameDifficulty difficulty) {
        generated.clear();
        opts.gameDifficulty = difficulty;
        opts.action = Action.GENERATE;
        opts.needNow = true;
        opts.printSolution = false;
        opts.threads = Runtime.getRuntime().availableProcessors();
        opts.gameType = type;
        doAction();
        return generated.poll();
    }

    public LinkedList<int[]> generateMultiple(GameType type, GameDifficulty difficulty, int amount) {
        generated.clear();
        opts.numberToGenerate = amount;
        opts.gameDifficulty = difficulty;
        opts.needNow = true;
        opts.action = Action.GENERATE;
        opts.printSolution = false;
        opts.threads = Runtime.getRuntime().availableProcessors();
        opts.gameType = type;
        doAction();
        return generated;
    }

    public int[] solve(GameBoard gameBoard) {

        level = new int[gameBoard.getSize() * gameBoard.getSize()];
        solveImpossible = false;

        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                if (gameBoard.getCell(i, j).isFixed()) {
                    level[gameBoard.getSize() * i + j] = gameBoard.getCell(i, j).getValue();
                }
            }
        }

        opts.needNow = true;
        opts.action = Action.SOLVE;
        opts.printSolution = true;
        opts.threads = 1;
        opts.gameType = gameBoard.getGameType();
        doAction();
        //if(solveImpossible) {
        // Can not occur with normal use of the app.
        //}
        return solution;
    }

    private void doAction() {
        // The number of puzzles solved or generated.
        final AtomicInteger puzzleCount = new AtomicInteger(0);
        final AtomicBoolean done = new AtomicBoolean(false);

        Thread[] threads = new Thread[opts.threads];
        for (int threadCount = 0; threadCount < threads.length; threadCount++) {
            threads[threadCount] = new Thread(
                    new Runnable() {

                        // Create a new puzzle board
                        // and set the options
                        private final QQWing ss = createQQWing();

                        private QQWing createQQWing() {
                            QQWing ss = new QQWing(opts.gameType, opts.gameDifficulty);
                            ss.setRecordHistory(opts.printHistory || opts.printInstructions || opts.printStats || opts.gameDifficulty != GameDifficulty.Unspecified);
                            ss.setLogHistory(opts.logHistory);
                            ss.setPrintStyle(opts.printStyle);
                            return ss;
                        }

                        @Override
                        public void run() {

                            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                            try {

                                // Solve puzzle or generate puzzles
                                // until end of input for solving, or
                                // until we have generated the specified number.
                                while (!done.get()) {

                                    // Record whether the puzzle was possible or
                                    // not,
                                    // so that we don't try to solve impossible
                                    // givens.
                                    boolean havePuzzle;

                                    if (opts.action == Action.GENERATE) {
                                        // Generate a puzzle
                                        havePuzzle = ss.generatePuzzleSymmetry(opts.symmetry);

                                    } else {
                                        // Read the next puzzle on STDIN
                                        int[] puzzle = new int[QQWing.BOARD_SIZE];
                                        if (getPuzzleToSolve(puzzle)) {
                                            havePuzzle = ss.setPuzzle(puzzle);
                                            if (havePuzzle) {
                                                puzzleCount.getAndDecrement();
                                            } else {
                                                // Puzzle to solve is impossible.
                                                solveImpossible = true;
                                            }
                                        } else {
                                            // Set loop to terminate when nothing is
                                            // left on STDIN
                                            havePuzzle = false;
                                            done.set(true);
                                        }
                                        puzzle = null;
                                    }

                                    int solutions = 0;

                                    if (havePuzzle) {

                                        // Count the solutions if requested.
                                        // (Must be done before solving, as it would
                                        // mess up the stats.)
                                        //if (opts.countSolutions) {
                                        //    solutions = ss.countSolutions();
                                        //}

                                        // Solve the puzzle
                                        if (opts.printSolution || opts.printHistory || opts.printStats || opts.printInstructions || opts.gameDifficulty != GameDifficulty.Unspecified) {
                                            ss.solve();
                                            solution = ss.getSolution();
                                        }

                                        // Bail out if it didn't meet the difficulty
                                        // standards for generation
                                        if (opts.action == Action.GENERATE) {
                                            if (opts.gameDifficulty != GameDifficulty.Unspecified && opts.gameDifficulty != ss.getDifficulty()) {
                                                havePuzzle = false;
                                                // check if other threads have
                                                // finished the job
                                                if (puzzleCount.get() >= opts.numberToGenerate)
                                                    done.set(true);
                                            } else {
                                                int numDone = puzzleCount.incrementAndGet();
                                                if (numDone >= opts.numberToGenerate)
                                                    done.set(true);
                                                if (numDone > opts.numberToGenerate)
                                                    havePuzzle = false;
                                            }
                                        }
                                        if (havePuzzle) {
                                            generated.add(ss.getPuzzle());
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("QQWing", "Exception Occured", e);
                            }
                        }

                    }
            );
            threads[threadCount].start();
        }

        if (opts.needNow) {
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean getPuzzleToSolve(int[] puzzle) {
        if (level != null) {
            if (puzzle.length == level.length) {
                System.arraycopy(level, 0, puzzle, 0, level.length);
            }
            level = null;
            return true;
        }
        return false;
    }

    private static class QQWingOptions {
        final boolean    printHistory      = false;
        final boolean    printInstructions = false;
        final boolean    logHistory        = false;
        final PrintStyle printStyle        = PrintStyle.READABLE;
        final boolean    printStats        = false;
        final Symmetry   symmetry          = Symmetry.NONE;
        // defaults for options
        boolean        needNow          = false;
        boolean        printPuzzle      = false;
        boolean        printSolution    = false;
        boolean        timer            = false;
        boolean        countSolutions   = false;
        Action         action           = Action.NONE;
        int            numberToGenerate = 1;
        GameDifficulty gameDifficulty   = GameDifficulty.Unspecified;
        GameType       gameType         = GameType.Unspecified;
        int            threads          = Runtime.getRuntime().availableProcessors();
    }

}
