/*
 * Copyright (c) 2021. Bradley M. Small
 * All Rights reserved.
 */

package com.small.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class App extends JFrame {
    private static final AppProperties appProperties = new AppProperties();
    private final GridPanel gridPanel = new GridPanel(
            appProperties.getPropertyInt("GRID_ROWS",10),
            appProperties.getPropertyInt("GRID_COLUMNS",10),
            appProperties.getPropertyInt("CELL_HEIGHT", 30),
            appProperties.getPropertyInt("CELL_WIDTH", 30));
    private final Timer timer = new Timer(appProperties.getPropertyInt("DELAY", 100),
            e -> this.gridPanel.doCycle());

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

    private void initGUI() {
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        panel.setBackground(Color.BLUE);
        panel.add(gridPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);

        JButton buttonRandomize = new JButton("Randomize");
        buttonRandomize.addActionListener(e -> gridPanel.randomGridInit());
        buttonPanel.add(buttonRandomize);

        buttonStop.addActionListener(e -> stop());
        buttonPanel.add(buttonStop);

        buttonGo.addActionListener(e -> go());
        buttonPanel.add(buttonGo);

        JButton buttonQuit = new JButton("Quit");
        buttonQuit.addActionListener(e -> this.processWindowEvent(
                new WindowEvent(
                        this, WindowEvent.WINDOW_CLOSING)));
        buttonPanel.add(buttonQuit);

        panel.add(buttonPanel);
        pack();

        setVisible(true);

        gridPanel.randomGridInit();
    }
}