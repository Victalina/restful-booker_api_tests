package com.herokuapp.restful_booker_tests.api;

import com.herokuapp.restful_booker_tests.config.TestConfig;
import com.herokuapp.restful_booker_tests.models.*;
import io.qameta.allure.Step;

import static com.herokuapp.restful_booker_tests.spec.Spec.requestSpec;
import static com.herokuapp.restful_booker_tests.spec.Spec.responseSpecStatusCode;
import static io.restassured.RestAssured.given;

public class Api {

  @Step("Создать новый auth токен для пользователя {login}")
  public String createToken(String login, String password) {

    AuthRequestModel authData = new AuthRequestModel(login, password);
    AuthResponseModel response =
            given(requestSpec)
                    .body(authData)
                    .when()
                    .post("/auth")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(AuthResponseModel.class);

    return response.getToken();
  }

  @Step("Создать новое бронирование")
  public Integer createBooking(String firstName, String lastName, Integer totalPrice, Boolean depositPaid,
                               String checkIn, String checkOut, String additionalNeeds) {

    BookingDatesRequest dates = new BookingDatesRequest(checkIn, checkOut);
    CreateBookingRequestModel bookingData = new CreateBookingRequestModel(
            firstName, lastName, totalPrice, depositPaid, dates, additionalNeeds);

    CreateBookingResponseModel response =
            given(requestSpec)
                    .body(bookingData)
                    .when()
                    .post("/booking")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(CreateBookingResponseModel.class);

    return response.getBookingId();
  }

  @Step("Удалить бронирование с id = {id}")
  public void deleteBooking(Integer id) {
    given(requestSpec)
            .auth().preemptive().basic(TestConfig.get("admin.login"), TestConfig.get("admin.password"))
            .when()
            .delete("/booking/" + id)
            .then()
            .spec(responseSpecStatusCode(201));
  }

  @Step("Проверить получение несуществующего бронирования с id = {id]")
  public void getNonExistentBooking(Integer id) {
    given(requestSpec)
            .when()
            .get("/booking/" + id)
            .then()
            .spec(responseSpecStatusCode(404));
  }
}
