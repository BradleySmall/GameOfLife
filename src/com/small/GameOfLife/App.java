package com.small.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;

public class App extends JFrame implements ActionListener {
    private static final int DELAY = 100;
    private static final int GRID_COLUMNS = 100;
    private static final int GRID_ROWS = 100;
    private static final int CELL_WIDTH = 9;
    private static final int CELL_HEIGHT = 9;

    private final Timer timer = new Timer(DELAY, this);
    private final GridPanel gridPanel = new GridPanel(GRID_ROWS, GRID_COLUMNS, CELL_HEIGHT, CELL_WIDTH);
    private final JButton buttonStop = new JButton("Stop");
    private final JButton buttonGo = new JButton("Go");

    public App() {
        initGUI();
    }

    private void stop() {
        timer.stop();
        buttonGo.setEnabled(true);
        buttonStop.setEnabled(false);
    }

    private void go() {
        timer.start();
        buttonGo.setEnabled(false);
        buttonStop.setEnabled(true);
    }

    private void randomGridInit() {
        Random rng = new Random();
        boolean[][] grid = gridPanel.getGrid();
        for (int row = 1; row < App.GRID_ROWS - 1; ++row) {
            for (int column = 1; column < App.GRID_COLUMNS - 1; ++column) {
                grid[row][column] = rng.nextInt() % 7 == 0;
            }
        }
        gridPanel.setGrid(grid);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand() == null) {
            doCycle();
        } else {
            switch (actionEvent.getActionCommand()) {
                case "Randomize":
                    randomGridInit();
                    break;
                case "Stop":
                    stop();
                    break;
                case "Go":
                    go();
                    break;
                case "Quit":
                    this.processWindowEvent(
                            new WindowEvent(
                                    this, WindowEvent.WINDOW_CLOSING));
                    break;
            }
        }
    }

    private boolean nextVal(int row, int column) {
        boolean[][] grid = gridPanel.getGrid();
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

    private void doCycle() {
        boolean[][] grid2 = new boolean[GRID_ROWS][GRID_COLUMNS];
        for (int row = 1; row < App.GRID_ROWS - 1; ++row) {
            for (int column = 1; column < App.GRID_COLUMNS - 1; ++column) {
                grid2[row][column] = nextVal(row, column);
            }
        }
        gridPanel.setGrid(grid2);
    }

    private void initGUI() {
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GRID_COLUMNS * CELL_WIDTH + 30,
                GRID_ROWS * CELL_HEIGHT + 110);

        JPanel panel = new JPanel();
        add(panel);
        panel.setBackground(Color.BLUE);
        panel.add(gridPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);

        JButton buttonRandomize = new JButton("Randomize");
        buttonRandomize.addActionListener(this);
        buttonPanel.add(buttonRandomize);

        buttonStop.addActionListener(this);
        buttonPanel.add(buttonStop);

        buttonGo.addActionListener(this);
        buttonPanel.add(buttonGo);

        JButton buttonQuit = new JButton("Quit");
        buttonQuit.addActionListener(this);
        buttonPanel.add(buttonQuit);

        panel.add(buttonPanel);
        pack();

        setVisible(true);

        randomGridInit();
    }

    public static void main(String[] args) {
        App app = new App();
        app.go();
    }
}