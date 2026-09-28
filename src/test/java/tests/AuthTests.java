package tests;

import com.herokuapp.restful_booker_tests.config.TestConfig;
import com.herokuapp.restful_booker_tests.models.AuthRequestModel;
import com.herokuapp.restful_booker_tests.models.AuthResponseModel;
import com.herokuapp.restful_booker_tests.models.ErrorResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

import static com.herokuapp.restful_booker_tests.spec.Spec.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.XML;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Тесты на аутентификацию")
public class AuthTests extends TestBase {

  @Test
  @DisplayName("Успешная аутентификация с корректными учетными данными")
  void successfulAuthShouldReturnTokenTest() {
    AuthRequestModel authData = new AuthRequestModel(TestConfig.get("admin.login"), TestConfig.get("admin.password"));
    AuthResponseModel response = step("Отправить запрос на аутентификацию с корректными учетными данными", () ->
            given(requestSpec)
                    .body(authData)
                    .when()
                    .post("/auth")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(AuthResponseModel.class));

    step("Проверить токен в ответе", () ->
            assertThat(response.getToken(), is(not(emptyOrNullString()))));
  }

  @Disabled("BUG001: /auth возвращает 200 вместо 401 при неверных учётных данных")
  @Test
  @DisplayName("Неуспешная аутентификация с некорректным логином")
  void authWithIncorrectUsernameShouldReturnErrorMessageTest() {
    AuthRequestModel authData = new AuthRequestModel("admin1", TestConfig.get("admin.password"));
    ErrorResponseModel response = step("Отправить запрос на аутентификацию с неправильным логином", () ->
            given(requestSpec)
                    .body(authData)
                    .when()
                    .post("/auth")
                    .then()
                    .spec(responseSpecStatusCode(401))
                    .extract().as(ErrorResponseModel.class));

    step("Проверить ошибку в ответе", () ->
            assertThat(response.getReason(), is("Bad credentials")));
  }

  @Disabled("BUG001: /auth возвращает 200 вместо 401 при неверных учётных данных")
  @Test
  @DisplayName("Неуспешная аутентификация с некорректным паролем")
  void authWithIncorrectPasswordShouldReturnErrorMessageTest() {
    AuthRequestModel authData = new AuthRequestModel(TestConfig.get("admin.login"), "password");
    ErrorResponseModel response = step("Отправить запрос на аутентификацию с неправильным паролем", () ->
            given(requestSpec)
                    .body(authData)
                    .when()
                    .post("/auth")
                    .then()
                    .spec(responseSpecStatusCode(401))
                    .extract().as(ErrorResponseModel.class));

    step("Проверить ошибку в ответе", () ->
            assertThat(response.getReason(), is("Bad credentials")));
  }

  @Disabled("BUG001: /auth возвращает 200 вместо 401 при неверных учётных данных")
  @Test
  @DisplayName("Неуспешная аутентификация с пустыми логином и паролем")
  void authWithEmptyUsernameAndPasswordShouldReturnErrorMessageTest() {
    AuthRequestModel authData = new AuthRequestModel("", "");
    ErrorResponseModel response = step("Отправить запрос на аутентификацию с пустыми логином и паролем", () ->
            given(requestSpec)
                    .body(authData)
                    .when()
                    .post("/auth")
                    .then()
                    .spec(responseSpecStatusCode(401))
                    .extract().as(ErrorResponseModel.class));

    step("Проверить ошибку в ответе", () ->
            assertThat(response.getReason(), is("Bad credentials")));
  }

  @Disabled("BUG002: /auth возвращает 200 вместо 400 при некорректном/отсутствующем теле запроса")
  @Test
  @DisplayName("Неуспешная аутентификация с пустым телом запроса")
  void authWithEmptyRequestBodyShouldReturnErrorMessageTest() {
    String emptyBody = "{}";
    ErrorResponseModel response = step("Отправить запрос на аутентификацию с пустым телом запроса", () ->
            given(requestSpec)
                    .body(emptyBody)
                    .when()
                    .post("/auth")
                    .then()
                    .spec(responseSpecStatusCode(400))
                    .extract().as(ErrorResponseModel.class));

    step("Проверить ошибку в ответе", () ->
            assertThat(response.getReason(), is("Bad credentials")));
  }

  @Disabled("BUG002: /auth возвращает 200 вместо 400 при некорректном/отсутствующем теле запроса")
  @Test
  @DisplayName("Неуспешная аутентификация с отсутствующим телом запроса")
  void authWithMissingRequestBodyShouldReturnErrorMessageTest() {
    ErrorResponseModel response = step("Отправить запрос на аутентификацию с отсутствующим телом запроса", () ->
            given(requestSpec)
                    .when()
                    .post("/auth")
                    .then()
                    .spec(responseSpecStatusCode(400))
                    .extract().as(ErrorResponseModel.class));

    step("Проверить ошибку в ответе", () ->
            assertThat(response.getReason(), is("Bad credentials")));
  }

  @Test
  @DisplayName("Неуспешная аутентификация с неверным телом запроса")
  void authWithWrongRequestBodyShouldReturnErrorMessageTest() {
    String wrongBody = "%}";
    step("Отправить запрос на аутентификацию с неверным телом запроса", () ->
            given(requestSpec)
                    .body(wrongBody)
                    .when()
                    .post("/auth")
                    .then()
                    .spec(responseSpecStatusCode(400)));
  }

  @Disabled("BUG003: /auth возвращает 200 вместо 415 при неверном contentType")
  @Test
  @DisplayName("Неуспешная аутентификация с отсутствующим телом запроса и неверным contentType")
  void authWithIncorrectContentTypeAndMissingRequestBodyShouldReturnErrorMessageTest() {
    ErrorResponseModel response = step("Отправить запрос на аутентификацию с отсутствующим телом запроса и неверным contentType", () ->
            given(requestSpecWithoutContentType)
                    .contentType(XML)
                    .when()
                    .post("/auth")
                    .then()
                    .spec(responseSpecStatusCode(415))
                    .extract().as(ErrorResponseModel.class));

    step("Проверить ошибку в ответе", () ->
            assertThat(response.getReason(), is("Bad credentials")));
  }
}
