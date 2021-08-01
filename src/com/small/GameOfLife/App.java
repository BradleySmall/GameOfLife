package com.small.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class App extends JFrame implements ActionListener {
    private static final AppProperties appProperties = new AppProperties();

    private final Timer timer = new Timer(appProperties.DELAY, this);
    private final GridPanel gridPanel = new GridPanel(appProperties.GRID_ROWS, appProperties.GRID_COLUMNS,
            appProperties.CELL_HEIGHT, appProperties.CELL_WIDTH);
    private final JButton buttonStop = new JButton("Stop");
    private final JButton buttonGo = new JButton("Go");

    public App() {
        initGUI();
    }

    public static void main(String[] args) {
        App app = new App();
        app.go();
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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand() == null) {
            gridPanel.doCycle();
        } else {
            switch (actionEvent.getActionCommand()) {
                case "Randomize":
                    gridPanel.randomGridInit();
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

    private void initGUI() {
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        gridPanel.randomGridInit();
    }
}