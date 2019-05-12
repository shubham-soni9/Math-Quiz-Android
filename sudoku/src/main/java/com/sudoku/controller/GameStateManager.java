package com.sudoku.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.sudoku.controller.helper.GameInfoContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris on 16.11.2015.
 */
public class GameStateManager {

    private static final int                     MAX_NUM_OF_SAVED_GAMES = 10;
    private static final String                  FILE_EXTENSION         = ".txt";
    private static final String                  SAVE_PREFIX            = "save_";
    private static final String                  SAVES_DIR              = "saves";
    private static       List<GameInfoContainer> list                   = new LinkedList<>();
    private final        Context                 context;
    private final        SharedPreferences       settings;

    public GameStateManager(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public static List<GameInfoContainer> getLoadableGameList() {
        return list;
    }

    public List<GameInfoContainer> loadGameStateInfo() {
        if (!settings.getBoolean("savesChanged", false)) {
            return list;
        }
        File dir = context.getDir(SAVES_DIR, 0);

        LinkedList<GameInfoContainer> result = new LinkedList<>();

        // go through every file
        for (File file : dir.listFiles()) {

            // filter so we only work with actual files
            if (file.isFile()) {

                // create a new GameInfoContainer
                GameInfoContainer gic = new GameInfoContainer();

                // load file
                byte[] bytes = new byte[(int) file.length()];
                try {
                    try (FileInputStream stream = new FileInputStream(file)) {
                        stream.read(bytes);
                    }
                } catch (IOException e) {
                    Log.e("File Manager", "Could not load game. IOException occured.");
                }
                // start parsing
                String gameString = new String(bytes);
                String[] values = gameString.split("/");

                try {
                    if (values.length < 4) {
                        throw new IllegalArgumentException("Can not load game info. File seems to be damaged or incomplete.");
                    }

                    // fill the container
                    String id = file.getName().substring(5, file.getName().lastIndexOf("."));
                    int i = 0;
                    gic.setID(Integer.valueOf(id));    // save_x.txt
                    gic.parseGameType(values[i++]);
                    gic.parseTime(values[i++]);
                    gic.parseDate(values[i++]);
                    gic.parseDifficulty(values[i++]);
                    gic.parseFixedValues(values[i++]);
                    gic.parseSetValues(values[i++]);
                    gic.parseNotes(values[i++]);
                    gic.parseHintsUsed(values[i++]);
                } catch (IllegalArgumentException e) {
                    file.delete();
                    continue;
                } catch (IndexOutOfBoundsException e) {
                    file.delete();
                    continue;
                }

                // then add it to the list
                result.add(gic);
            }
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("savesChanged", false);
        editor.commit();

        list = sortListByLastPlayedDate(result);

        LinkedList<GameInfoContainer> removeList = new LinkedList<>();

        for (int i = 0; i < list.size(); i++) {
            if (i >= MAX_NUM_OF_SAVED_GAMES) {
                deleteGameStateFile(list.get(i));
                removeList.add(list.get(i));
            }
        }

        for (GameInfoContainer gic : removeList) {
            list.remove(gic);
        }

        return list;
    }

    public void deleteGameStateFile(GameInfoContainer gic) {
        File dir = context.getDir(SAVES_DIR, 0);

        File file = new File(dir, SAVE_PREFIX + gic.getID() + FILE_EXTENSION);

        file.delete();

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("savesChanged", true);
        editor.commit();

    }

    private LinkedList<GameInfoContainer> sortListByLastPlayedDate(LinkedList<GameInfoContainer> list) {

        if (list.size() < 2) {
            return list;
        }

        LinkedList<GameInfoContainer> listL = new LinkedList<>();
        LinkedList<GameInfoContainer> listR = new LinkedList<>();

        int middle = list.size() / 2;

        for (int i = 0; i < list.size(); i++) {
            if (i < middle) {
                listL.add(list.get(i));
            } else {
                listR.add(list.get(i));
            }
        }

        listL = sortListByLastPlayedDate(listL);
        listR = sortListByLastPlayedDate(listR);

        return sortListByLastPlayedDateMerge(listL, listR);
    }

    private LinkedList<GameInfoContainer> sortListByLastPlayedDateMerge(LinkedList<GameInfoContainer> list1, LinkedList<GameInfoContainer> list2) {

        LinkedList<GameInfoContainer> result = new LinkedList<>();

        while (!(list1.isEmpty() && list2.isEmpty())) {
            GameInfoContainer gic1 = list1.peek();
            GameInfoContainer gic2 = list2.peek();
            if (gic1 == null) {
                result.add(list2.pop());
            } else if (gic2 == null) {
                result.add(list1.pop());
            } else if (gic1.getLastTimePlayed().after(gic2.getLastTimePlayed())) {
                result.add(list1.pop());
            } else {
                result.add(list2.pop());
            }
        }

        return result;
    }

    public void saveGameState(GameController controller) {
        String level = GameInfoContainer.getGameInfo(controller);

        File dir = context.getDir(SAVES_DIR, 0);

        //controller.getGameID()

        File file = new File(dir, SAVE_PREFIX + controller.getGameID() + FILE_EXTENSION);

        try {
            try (FileOutputStream stream = new FileOutputStream(file)) {
                stream.write(level.getBytes());
            }
        } catch (IOException e) {
            Log.e("File Manager", "Could not save game. IOException occured.");
        }

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("savesChanged", true);
        editor.commit();
    }
}
