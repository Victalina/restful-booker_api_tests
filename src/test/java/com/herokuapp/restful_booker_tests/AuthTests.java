package com.herokuapp.restful_booker_tests;

import com.herokuapp.restful_booker_tests.models.AuthRequestModel;
import com.herokuapp.restful_booker_tests.models.AuthResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AuthTests extends TestBase {

  @Test
  void successfulAuthShouldReturnTokenTest(){
    AuthRequestModel authData = new AuthRequestModel(login, password);
    AuthResponseModel response = given()
            .contentType(JSON)
            .body(authData)
            .log().all()
            .when()
            .post("/auth")
            .then()
            .log().all()
            .statusCode(200)
            .extract().as(AuthResponseModel.class);

    assertThat(response.getToken(),is(not(emptyOrNullString())));
  }
}
