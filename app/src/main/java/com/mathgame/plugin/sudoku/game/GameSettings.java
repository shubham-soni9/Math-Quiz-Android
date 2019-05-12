package com.mathgame.plugin.sudoku.game;

/**
 * Created by Chris on 09.11.2015.
 */
class GameSettings {


    public static boolean getEnableAutomaticNoteDeletion() {
        boolean enableAutomaticNoteDeletion = true;
        return enableAutomaticNoteDeletion;
    }

    public static boolean getHighlightConnectedRow() {
        boolean highlightConnectedRow = true;
        return highlightConnectedRow;
    }

    public static boolean getHighlightConnectedColumn() {
        boolean highlightConnectedColumn = true;
        return highlightConnectedColumn;
    }

    public static boolean getHighlightConnectedSection() {
        boolean highlightConnectedSection = true;
        return highlightConnectedSection;
    }
}
