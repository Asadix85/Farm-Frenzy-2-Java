package org.example.game_farmfrenzy2.model.game;

import org.example.game_farmfrenzy2.model.entities.*;
import java.util.*;

public class GridManager {
    public static final int ROWS = 6;
    public static final int COLS = 5;

    private Object[][] grid;

    public GridManager() {
        grid = new Object[ROWS][COLS];
    }

    public Object getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, Object obj) {
        grid[row][col] = obj;
    }

    public boolean isGrassArea(int row, int col) {
        return row >= 1 && row <= 4 && col >= 1 && col <= 3;
    }

    public boolean isBorder(int row, int col) {
        return row == 0 || row == ROWS-1 || col == 0 || col == COLS-1;
    }

    public void clearCell(int row, int col) {
        grid[row][col] = null;
    }

    public boolean isOccupied(int row, int col) {
        return grid[row][col] != null;
    }

    public Position findEmptyBorderCell() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (isBorder(r, c) && !isOccupied(r, c)) {
                    return new Position(r, c);
                }
            }
        }
        return null;
    }

    public List<Object> getAllObjects() {
        List<Object> objects = new ArrayList<>();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] != null) {
                    objects.add(grid[r][c]);
                }
            }
        }
        return objects;
    }
}