package com.herokuapp.restful_booker_tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

  protected String login = System.getProperty("login");
  protected String password = System.getProperty("password");

  @BeforeAll
  static void setUpConfig() {

    RestAssured.baseURI = "https://restful-booker.herokuapp.com";

  }
}
