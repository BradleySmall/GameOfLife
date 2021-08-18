package com.small.gameoflife;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
    public static final String DELAY = "DELAY";
    public static final String GRID_COLUMNS = "GRID_COLUMNS";
    public static final String GRID_ROWS = "GRID_ROWS";
    public static final String CELL_WIDTH = "CELL_WIDTH";
    public static final String CELL_HEIGHT = "CELL_HEIGHT";
    private int timerDelay;
    private int gridColumns;
    private int gridRows;
    private int cellWidth;
    private int cellHeight;

    public AppProperties() {
        Properties defaultProps = new java.util.Properties();
        try (FileInputStream in = new FileInputStream("defaultProperties")) {
            defaultProps.load(in);
        } catch (FileNotFoundException e) {
            defaultProps.setProperty(DELAY, "100");
            defaultProps.setProperty(GRID_COLUMNS, "100");
            defaultProps.setProperty(GRID_ROWS, "100");
            defaultProps.setProperty(CELL_WIDTH, "9");
            defaultProps.setProperty(CELL_HEIGHT, "9");

            try (FileOutputStream out = new FileOutputStream("defaultProperties")) {
                defaultProps.store(out, "---No Comment---");
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties applicationProps = new java.util.Properties(defaultProps);
        try (FileInputStream in = new FileInputStream("appProperties")) {
            applicationProps.load(in);
        } catch (FileNotFoundException e) {
            try (FileOutputStream out = new FileOutputStream("appProperties")) {
                applicationProps.store(out, "---No Comment---");
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        timerDelay = Integer.parseInt(applicationProps.getProperty(DELAY));
        gridColumns = Integer.parseInt(applicationProps.getProperty(GRID_COLUMNS));
        gridRows = Integer.parseInt(applicationProps.getProperty(GRID_ROWS));
        cellWidth = Integer.parseInt(applicationProps.getProperty(CELL_WIDTH));
        cellHeight = Integer.parseInt(applicationProps.getProperty(CELL_HEIGHT));
    }

    public int getPropertyInt(String key, int defaultValue) {
        return switch (key) {
            case DELAY -> timerDelay;
            case GRID_COLUMNS -> gridColumns;
            case GRID_ROWS -> gridRows;
            case CELL_WIDTH -> cellWidth;
            case CELL_HEIGHT -> cellHeight;
            default -> defaultValue;
        };
    }
}
