package com.mathgame.plugin.slidegame.slideview;

import java.util.ArrayList;

public class GameGrid {

    private final Tile[][] mGrid;
    private final Tile[][] mUndoGrid;
    private final Tile[][] mBufferGrid;

    public GameGrid(int sizeX, int sizeY) {
        mGrid = new Tile[sizeX][sizeY];
        mUndoGrid = new Tile[sizeX][sizeY];
        mBufferGrid = new Tile[sizeX][sizeY];
        clearGrid();
        clearUndoGrid();
    }

    public Position randomAvailableCell() {
        ArrayList<Position> availableCells = getAvailableCells();
        if (availableCells.size() >= 1) {
            return availableCells.get((int) Math.floor(Math.random() * availableCells.size()));
        }
        return null;
    }

    private ArrayList<Position> getAvailableCells() {
        ArrayList<Position> availableCells = new ArrayList<>();
        for (int xx = 0; xx < mGrid.length; xx++) {
            for (int yy = 0; yy < mGrid[0].length; yy++) {
                if (mGrid[xx][yy] == null) {
                    availableCells.add(new Position(xx, yy));
                }
            }
        }
        return availableCells;
    }

    public boolean isCellsAvailable() {
        return (getAvailableCells().size() >= 1);
    }

    public boolean isCellAvailable(Position cell) {
        return !isCellOccupied(cell);
    }

    public boolean isCellOccupied(Position cell) {
        return (getTile(cell) != null);
    }

    public Tile getTile(Position cell) {
        if (cell != null && isCellWithinBounds(cell)) {
            return mGrid[cell.getX()][cell.getY()];
        } else {
            return null;
        }
    }

    public Tile getCellContent(int x, int y) {
        if (isCellWithinBounds(x, y)) {
            return mGrid[x][y];
        } else {
            return null;
        }
    }

    public boolean isCellWithinBounds(Position cell) {
        return 0 <= cell.getX() && cell.getX() < mGrid.length
                && 0 <= cell.getY() && cell.getY() < mGrid[0].length;
    }

    private boolean isCellWithinBounds(int x, int y) {
        return 0 <= x && x < mGrid.length
                && 0 <= y && y < mGrid[0].length;
    }

    public void insertTile(Tile tile) {
        mGrid[tile.getX()][tile.getY()] = tile;
    }

    public void removeTile(Tile tile) {
        mGrid[tile.getX()][tile.getY()] = null;
    }

    public void saveTiles() {
        for (int xx = 0; xx < mBufferGrid.length; xx++) {
            for (int yy = 0; yy < mBufferGrid[0].length; yy++) {
                if (mBufferGrid[xx][yy] == null) {
                    mUndoGrid[xx][yy] = null;
                } else {
                    mUndoGrid[xx][yy] = new Tile(xx, yy, mBufferGrid[xx][yy].getValue());
                }
            }
        }
    }

    public void prepareSaveTiles() {
        for (int xx = 0; xx < mGrid.length; xx++) {
            for (int yy = 0; yy < mGrid[0].length; yy++) {
                if (mGrid[xx][yy] == null) {
                    mBufferGrid[xx][yy] = null;
                } else {
                    mBufferGrid[xx][yy] = new Tile(xx, yy, mGrid[xx][yy].getValue());
                }
            }
        }
    }

    public void revertTiles() {
        for (int xx = 0; xx < mUndoGrid.length; xx++) {
            for (int yy = 0; yy < mUndoGrid[0].length; yy++) {
                if (mUndoGrid[xx][yy] == null) {
                    mGrid[xx][yy] = null;
                } else {
                    mGrid[xx][yy] = new Tile(xx, yy, mUndoGrid[xx][yy].getValue());
                }
            }
        }
    }

    public void clearGrid() {
        for (int xx = 0; xx < mGrid.length; xx++) {
            for (int yy = 0; yy < mGrid[0].length; yy++) {
                mGrid[xx][yy] = null;
            }
        }
    }

    private void clearUndoGrid() {
        for (int xx = 0; xx < mGrid.length; xx++) {
            for (int yy = 0; yy < mGrid[0].length; yy++) {
                mUndoGrid[xx][yy] = null;
            }
        }
    }

    public Tile[][] getGrid() {
        return mGrid;
    }

    public Tile[][] getUndoGrid() {
        return mUndoGrid;
    }

}
