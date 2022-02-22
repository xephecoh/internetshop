package com.khamutov.configuration;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private final Properties properties;

    public PropertiesReader(String path) {
        properties = new Properties();
        readProperties(path);
    }

    private void readProperties(String path) {
        try (FileReader inputStream = new FileReader(path)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can not read properties from " + path);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
