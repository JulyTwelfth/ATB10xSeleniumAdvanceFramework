package com.thetestingacademy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesReader {

    private static final Properties PROPERTIES = loadProperties();

    private PropertiesReader() {
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = PropertiesReader.class.getClassLoader()
                .getResourceAsStream("data.properties")) {
            if (input == null) {
                throw new IllegalStateException("data.properties was not found on the classpath");
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load data.properties", exception);
        }
    }

    public static String readKey(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        String environmentValue = System.getenv(key.toUpperCase().replace('.', '_'));
        if (environmentValue != null && !environmentValue.isBlank()) {
            return environmentValue;
        }

        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing configuration key: " + key);
        }
        return value.trim();
    }
}
