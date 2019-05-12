package com.sudoku.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.sudoku.controller.database.DatabaseHelper;
import com.sudoku.controller.database.model.Level;
import com.sudoku.game.GameDifficulty;
import com.sudoku.game.GameType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Chris on 23.11.2015.
 */
public class NewLevelManager {

    public static  int             PRE_SAVES_MIN  = 3;
    public static  int             PRE_SAVES_MAX  = 10;
    private static NewLevelManager instance;
    private static String          FILE_EXTENSION = ".txt";
    private static String          LEVEL_PREFIX   = "level_";
    private static String          LEVELS_DIR     = "level";
    private static File            DIR;
    Context context;
    private SharedPreferences settings;
    private DatabaseHelper    dbHelper;

    private NewLevelManager(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
        this.dbHelper = new DatabaseHelper(context);
        DIR = context.getDir(LEVELS_DIR, 0);
    }

    public static NewLevelManager getInstance(Context context, SharedPreferences settings) {
        if (instance == null) {
            instance = new NewLevelManager(context, settings);
        }
        return instance;
    }

    public boolean isLevelLoadable(GameType type, GameDifficulty diff) {
        return dbHelper.getLevels(diff, type).size() > 0;
    }

    @Deprecated
    public boolean isLevelLoadableOld(GameType type, GameDifficulty diff) {
        for (File file : DIR.listFiles()) {
            if (file.isFile()) {
                String name = file.getName().substring(0, file.getName().lastIndexOf("_"));
                StringBuilder sb = new StringBuilder();
                sb.append(LEVEL_PREFIX);
                sb.append(type.name());
                sb.append("_");
                sb.append(diff.name());

                if (name.equals(sb.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] loadLevel(GameType type, GameDifficulty diff) {
        Level level = dbHelper.getLevel(diff, type);
        dbHelper.deleteLevel(level.getId());
        return level.getPuzzle();

//        for(Level level : levels) {
//
//            // delete level from database
//            dbHelper.deleteLevel(level.getId());
//
//            // test if it has the correct length
//            int length = type.getSize() * type.getSize();
//            if (level.getPuzzle().length != length) {
//                // Level is not correctly saved -> discard it and try again
//                continue;
//            } else {
//                return level.getPuzzle();
//            }
//        }
//
//        // if there is no level or every level has the wrong length
//        throw new RuntimeException("No level to load with specified parameters");
    }

    @Deprecated
    public int[] loadLevelOld(GameType type, GameDifficulty diff) {
        List<int[]> result = new LinkedList<>();
        LinkedList<Integer> availableFiles = new LinkedList<>();
        Random r = new Random();

        // go through every file
        for (File file : DIR.listFiles()) {

            // filter so we only work with actual files
            if (file.isFile()) {
                String name = file.getName().substring(0, file.getName().lastIndexOf("_"));
                String number = file.getName().substring(file.getName().lastIndexOf("_") + 1, file.getName().lastIndexOf("."));

                StringBuilder sb = new StringBuilder();
                sb.append(LEVEL_PREFIX);
                sb.append(type.name());
                sb.append("_");
                sb.append(diff.name());

                // if file is a level for our gametype and difficulty .. load it
                if (name.equals(sb.toString())) {

                    // load file
                    byte[] bytes = new byte[(int) file.length()];
                    try {
                        FileInputStream stream = new FileInputStream(file);
                        try {
                            stream.read(bytes);
                        } finally {
                            stream.close();
                        }
                    } catch (IOException e) {
                        Log.e("File Manager", "Could not load game. IOException occured.");
                    }

                    // start parsing
                    String gameString = new String(bytes);

                    int[] puzzle = new int[type.getSize() * type.getSize()];

                    if (puzzle.length != gameString.length()) {
                        throw new IllegalArgumentException("Saved level is does not have the correct size.");
                    }

                    for (int i = 0; i < gameString.length(); i++) {
                        puzzle[i] = Symbol.getValue(Symbol.SaveFormat, String.valueOf(gameString.charAt(i))) + 1;
                    }
                    availableFiles.add(Integer.valueOf(number));
                    result.add(puzzle);
                }
            }
        }

        if (result.size() > 0) {
            int i = r.nextInt(availableFiles.size());
            int chosen = availableFiles.get(i);
            int[] resultPuzzle = result.get(i);

            StringBuilder sb = new StringBuilder();
            sb.append(LEVEL_PREFIX);
            sb.append(type.name());
            sb.append("_");
            sb.append(diff.name());
            sb.append("_");
            sb.append(chosen);
            sb.append(FILE_EXTENSION);
            String filename = sb.toString();

            // select and delete the file
            File file = new File(DIR, filename);
            file.delete();

            // then return the puzzle to load it
            return resultPuzzle;
        }

        return null;
    }

    public void checkAndRestock() {
        Intent i = new Intent(context, GeneratorService.class);
        i.setAction(GeneratorService.ACTION_GENERATE);
        context.startService(i);
    }

}

