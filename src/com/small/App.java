package com.small;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;

public class App extends JFrame implements ActionListener {
    private static final int DELAY = 100;
    private static final int gridColumns = 100;
    private static final int gridRows = 100;
    private static final int cellWidth = 9;
    private static final int cellHeight = 9;

    private final Timer timer = new Timer(DELAY, this);
    private final GridPanel gridPanel =
            new GridPanel(gridRows, gridColumns, cellHeight, cellWidth);

    private JButton buttonStop;
    private JButton buttonGo;

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
        for (int row = 1; row < App.gridRows - 1; ++row) {
            for (int column = 1; column < App.gridColumns - 1; ++column) {
                grid[row][column] = rng.nextInt() % 7 == 0;
            }
        }
        gridPanel.setGrid(grid);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand() == null) {
            doCycle();
        } else if (actionEvent.getActionCommand().equals("Randomize")) {
            randomGridInit();
        } else if (actionEvent.getActionCommand().equals("Stop")) {
            stop();
        } else if (actionEvent.getActionCommand().equals("Go")) {
            go();
        } else if (actionEvent.getActionCommand().equals("Quit")) {
            this.processWindowEvent(
                    new WindowEvent(
                            this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private boolean nextVal(int row, int column) {
        boolean[][] grid = gridPanel.getGrid();
        boolean currentState = grid[row][column];
        int liveNeighbors = 0;
        liveNeighbors += grid[ row    ][column - 1] ? 1 : 0;
        liveNeighbors += grid[ row    ][column + 1] ? 1 : 0;
        liveNeighbors += grid[ row - 1][column    ] ? 1 : 0;
        liveNeighbors += grid[ row + 1][column    ] ? 1 : 0;
        liveNeighbors += grid[ row - 1][column - 1] ? 1 : 0;
        liveNeighbors += grid[ row + 1][column - 1] ? 1 : 0;
        liveNeighbors += grid[ row - 1][column + 1] ? 1 : 0;
        liveNeighbors += grid[ row + 1][column + 1] ? 1 : 0;

        return (currentState && liveNeighbors == 2) || liveNeighbors == 3;
    }

    private void doCycle() {
        boolean[][] grid2 = new boolean[gridRows][gridColumns];
        for (int row = 1; row < App.gridRows - 1; ++row) {
            for (int column = 1; column < App.gridColumns - 1; ++column) {
                grid2[row][column] = nextVal(row, column);
            }
        }
        gridPanel.setGrid(grid2);
    }

    private void initGUI() {
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(gridColumns * cellWidth + 30,
                gridRows * cellHeight + 110);

        JPanel panel = new JPanel();
        add(panel);
        panel.setBackground(Color.BLUE);
        panel.add(gridPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);

        JButton buttonRandomize = new JButton("Randomize");
        buttonRandomize.addActionListener(this);
        buttonPanel.add(buttonRandomize);

        buttonStop = new JButton("Stop");
        buttonStop.addActionListener(this);
        buttonPanel.add(buttonStop);

        buttonGo = new JButton("Go");
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