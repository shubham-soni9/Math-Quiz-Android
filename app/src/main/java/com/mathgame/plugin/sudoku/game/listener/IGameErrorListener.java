package com.mathgame.plugin.sudoku.game.listener;

import com.mathgame.plugin.sudoku.game.CellConflict;

import java.util.List;

/**
 * Created by Chris on 02.02.2016.
 */
public interface IGameErrorListener {
    void onGameFilledWithErrors(List<CellConflict> errorList);
}
