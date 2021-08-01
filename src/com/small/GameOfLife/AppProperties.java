package com.small.GameOfLife;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
    public int DELAY;
    public int GRID_COLUMNS;
    public int GRID_ROWS;
    public int CELL_WIDTH;
    public int CELL_HEIGHT;

    public AppProperties() {
        Properties defaultProps = new java.util.Properties();
        try (FileInputStream in = new FileInputStream("defaultProperties")) {
            defaultProps.load(in);
        } catch (FileNotFoundException e) {
            defaultProps.setProperty("DELAY", "100");
            defaultProps.setProperty("GRID_COLUMNS", "100");
            defaultProps.setProperty("GRID_ROWS", "100");
            defaultProps.setProperty("CELL_WIDTH", "9");
            defaultProps.setProperty("CELL_HEIGHT", "9");

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

        DELAY = Integer.parseInt(applicationProps.getProperty("DELAY"));
        GRID_COLUMNS = Integer.parseInt(applicationProps.getProperty("GRID_COLUMNS"));
        GRID_ROWS = Integer.parseInt(applicationProps.getProperty("GRID_ROWS"));
        CELL_WIDTH = Integer.parseInt(applicationProps.getProperty("CELL_WIDTH"));
        CELL_HEIGHT = Integer.parseInt(applicationProps.getProperty("CELL_HEIGHT"));
    }
}
