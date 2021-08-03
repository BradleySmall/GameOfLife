package com.small.GameOfLife;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
    public static final Properties DEFAULT_PROPERTIES = new Properties();

    static {
        DEFAULT_PROPERTIES.setProperty("DELAY", "100");
        DEFAULT_PROPERTIES.setProperty("GRID_COLUMNS", "100");
        DEFAULT_PROPERTIES.setProperty("GRID_ROWS", "100");
        DEFAULT_PROPERTIES.setProperty("CELL_WIDTH", "9");
        DEFAULT_PROPERTIES.setProperty("CELL_HEIGHT", "9");
    }

    public int DELAY;
    public int GRID_COLUMNS;
    public int GRID_ROWS;
    public int CELL_WIDTH;
    public int CELL_HEIGHT;

    public AppProperties() {
        Properties applicationProps = new java.util.Properties(getProperties(DEFAULT_PROPERTIES, "defaultProperties"));
        getProperties(applicationProps, "appProperties");

        DELAY = Integer.parseInt(applicationProps.getProperty("DELAY"));
        GRID_COLUMNS = Integer.parseInt(applicationProps.getProperty("GRID_COLUMNS"));
        GRID_ROWS = Integer.parseInt(applicationProps.getProperty("GRID_ROWS"));
        CELL_WIDTH = Integer.parseInt(applicationProps.getProperty("CELL_WIDTH"));
        CELL_HEIGHT = Integer.parseInt(applicationProps.getProperty("CELL_HEIGHT"));
    }

    private Properties getProperties(Properties defaultProperties, String propertyFileName) {
        try {
            loadProperties(defaultProperties, propertyFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultProperties;
    }

    private Properties loadProperties(Properties defaultProperties, String propertyFileName) throws IOException {
        try (FileInputStream in = new FileInputStream(propertyFileName)) {
            defaultProperties.load(in);
        } catch (FileNotFoundException e) {

            try (FileOutputStream out = new FileOutputStream(propertyFileName)) {
                defaultProperties.store(out, "---No Comment---");
            }
        }
        return defaultProperties;
    }
}
