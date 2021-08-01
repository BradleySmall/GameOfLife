package com.small;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    private boolean[][] grid;
    private final int gridRows;
    private final int gridColumns;
    private final int cellHeight;
    private final int cellWidth;

    public GridPanel(int gridRows, int gridColumns, int cellHeight, int cellWidth) {
        this.gridRows = gridRows;
        this.gridColumns = gridColumns;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;

        Dimension size = new Dimension(this.gridColumns * this.cellWidth, this.gridRows * this.cellHeight);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        grid = new boolean [this.gridRows][this.gridColumns] ;
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int row = 1; row < gridRows -1; ++row) {
            for (int column = 1; column < gridColumns -1; ++column) {
                int y = row * cellHeight;
                int x = column * cellWidth;
                drawIt(g, x, y, getGrid()[row][column]);
            }
        }
    }

    private void drawIt(Graphics g, int x, int y, boolean isOn) {
        g.setColor(isOn ? Color.RED : Color.BLACK);
        g.fillRect(x, y, cellWidth, cellHeight);

        g.setColor(Color.WHITE);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void setGrid(boolean[][] grid) {
        this.grid = grid;
        this.repaint();
    }
}
