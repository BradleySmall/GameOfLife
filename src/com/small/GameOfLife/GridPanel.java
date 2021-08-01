package com.small.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class GridPanel extends JPanel implements MouseListener {
    private final boolean[][] grid;
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

        grid = new boolean[this.gridRows][this.gridColumns];
        addMouseListener(this);
    }

    public void randomGridInit() {
        Random rng = new Random();
        for (int row = 1; row < gridRows - 1; ++row) {
            for (int column = 1; column < gridColumns - 1; ++column) {
                grid[row][column] = rng.nextInt() % 7 == 0;
            }
        }
    }

    private boolean nextVal(int row, int column) {
        boolean currentState = grid[row][column];
        int liveNeighbors = 0;

        for (int neighborRow = row - 1; neighborRow <= row + 1; ++neighborRow) {
            for (int neighborColumn = column - 1; neighborColumn <= column + 1; ++neighborColumn) {
                if (neighborRow == row && neighborColumn == column) {
                    continue;
                }
                liveNeighbors += grid[neighborRow][neighborColumn] ? 1 : 0;
            }
        }
        return (currentState && liveNeighbors == 2) || liveNeighbors == 3;
    }

    public void doCycle() {
        boolean[][] nextGrid = new boolean[gridRows][gridColumns];
        for (int row = 1; row < gridRows - 1; ++row) {
            for (int column = 1; column < gridColumns - 1; ++column) {
                nextGrid[row][column] = nextVal(row, column);
            }
        }
        setGrid(nextGrid);
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int row = 1; row < gridRows - 1; ++row) {
            for (int column = 1; column < gridColumns - 1; ++column) {
                int y = row * cellHeight;
                int x = column * cellWidth;
                drawIt(g, x, y, grid[row][column]);
            }
        }
    }

    private void drawIt(Graphics g, int x, int y, boolean isOn) {
        g.setColor(isOn ? Color.RED : Color.BLACK);
        g.fillRect(x, y, cellWidth, cellHeight);

        g.setColor(Color.WHITE);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    public void setGrid(boolean[][] grid) {
        for (int row = 0; row < gridRows; ++row) {
            for (int column = 0; column < gridColumns; ++column) {
                this.grid[row][column] = grid[row][column];
            }
        }
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //JOptionPane.showMessageDialog(null, "This is a mouseClicked event at " + gridPoint.toString());
        Point point = mouseEvent.getPoint();
        int row = point.y / cellHeight;
        int column = point.x / cellWidth;
        grid[row][column] = !grid[row][column];
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        //JOptionPane.showMessageDialog(null, "This is a mousePressed event");

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        //JOptionPane.showMessageDialog(null, "This is a mouseReleased event");

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        //JOptionPane.showMessageDialog(null, "This is a mouseEntered event");

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        //JOptionPane.showMessageDialog(null, "This is a mouseExited event");

    }
}
