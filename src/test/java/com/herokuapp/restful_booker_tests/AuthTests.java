package com.herokuapp.restful_booker_tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class AuthTests extends TestBase {

  @Test
  void successfulAuthTest(){

    String authData = "{\"username\":\"" + login + "\", \"password\":\"" + password + "\"}";
    given()
            .contentType(JSON)
            .body(authData)
            .log().all()
            .when()
            .post("https://restful-booker.herokuapp.com/auth")
            .then()
            .log().all()
            .statusCode(200)
            .body("token", notNullValue());
  }
}
