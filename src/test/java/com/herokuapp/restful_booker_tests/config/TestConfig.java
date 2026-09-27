package com.herokuapp.restful_booker_tests.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

  private static final Properties properties = new Properties();

  static {
    try (InputStream inputStream = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {

      if (inputStream != null) {
        properties.load(inputStream);
      }

    } catch (IOException e) {
      throw new RuntimeException("Failed to load config.properties", e);
    }
  }

  public static String get(String key) {
    return System.getProperty(key, properties.getProperty(key));
  }
}
