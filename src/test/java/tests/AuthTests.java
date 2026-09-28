package tests;

import com.herokuapp.restful_booker_tests.config.TestConfig;
import com.herokuapp.restful_booker_tests.models.AuthRequestModel;
import com.herokuapp.restful_booker_tests.models.AuthResponseModel;
import com.herokuapp.restful_booker_tests.models.ErrorResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.XML;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AuthTests extends TestBase {

  @Test
  void successfulAuthShouldReturnTokenTest() {
    AuthRequestModel authData = new AuthRequestModel(TestConfig.get("admin.login"), TestConfig.get("admin.password"));
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
  @Disabled("BUG001: /auth возвращает 200 вместо 401 при неверных учётных данных")
  @Test
  void authWithIncorrectUsernameShouldReturnErrorMessageTest() {
    AuthRequestModel authData = new AuthRequestModel("admin1", TestConfig.get("admin.password"));
    ErrorResponseModel response = given()
            .contentType(JSON)
            .body(authData)
            .log().all()
            .when()
            .post("/auth")
            .then()
            .log().all()
            .statusCode(401)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getReason(),is("Bad credentials"));
  }

  @Disabled("BUG001: /auth возвращает 200 вместо 401 при неверных учётных данных")
  @Test
  void authWithIncorrectPasswordShouldReturnErrorMessageTest() {
    AuthRequestModel authData = new AuthRequestModel(TestConfig.get("admin.login"), "password");
    ErrorResponseModel response = given()
            .contentType(JSON)
            .body(authData)
            .log().all()
            .when()
            .post("/auth")
            .then()
            .log().all()
            .statusCode(401)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getReason(),is("Bad credentials"));
  }

  @Disabled("BUG001: /auth возвращает 200 вместо 401 при неверных учётных данных")
  @Test
  void authWithEmptyUsernameAndPasswordShouldReturnErrorMessageTest() {
    AuthRequestModel authData = new AuthRequestModel("", "");
    ErrorResponseModel response = given()
            .contentType(JSON)
            .body(authData)
            .log().all()
            .when()
            .post("/auth")
            .then()
            .log().all()
            .statusCode(401)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getReason(),is("Bad credentials"));
  }

  @Disabled("BUG002: /auth возвращает 200 вместо 400 при некорректном/отсутствующем теле запроса")
  @Test
  void authWithEmptyRequestBodyShouldReturnErrorMessageTest() {
    String emptyBody = "{}";
    ErrorResponseModel response = given()
            .contentType(JSON)
            .body(emptyBody)
            .log().all()
            .when()
            .post("/auth")
            .then()
            .log().all()
            .statusCode(400)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getReason(),is("Bad credentials"));
  }

  @Disabled("BUG002: /auth возвращает 200 вместо 400 при некорректном/отсутствующем теле запроса")
  @Test
  void authWithMissingRequestBodyShouldReturnErrorMessageTest() {
    ErrorResponseModel response = given()
            .contentType(JSON)
            .log().all()
            .when()
            .post("/auth")
            .then()
            .log().all()
            .statusCode(400)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getReason(),is("Bad credentials"));
  }

  @Test
  void authWithWrongRequestBodyShouldReturnErrorMessageTest() {
    String wrongBody = "%}";
    given()
            .contentType(JSON)
            .body(wrongBody)
            .log().all()
            .when()
            .post("/auth")
            .then()
            .log().all()
            .statusCode(400);
  }

  @Disabled("BUG003: /auth возвращает 200 вместо 415 при неверном contentType")
  @Test
  void authWithIncorrectContentTypeAndMissingRequestBodyShouldReturnErrorMessageTest() {
    ErrorResponseModel response = given()
            .contentType(XML)
            .log().all()
            .when()
            .post("/auth")
            .then()
            .log().all()
            .statusCode(415)
            .extract().as(ErrorResponseModel.class);

    assertThat(response.getReason(),is("Bad credentials"));
  }
}
