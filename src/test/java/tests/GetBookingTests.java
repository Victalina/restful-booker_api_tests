package tests;

import com.herokuapp.restful_booker_tests.models.BookingResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.herokuapp.restful_booker_tests.spec.Spec.requestSpec;
import static com.herokuapp.restful_booker_tests.spec.Spec.responseSpecStatusCode;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Получение бронирования")
public class GetBookingTests extends TestBase {

  @DisplayName("Успешное получение бронирования")
  @Test
  void getExistingBookingTest() {

    TestData testData = new TestData();

    id = api.createBooking(testData.getFirstName(), testData.getLastName(), testData.getTotalPrice(),
            testData.getDepositPaid(), testData.getCheckIn(), testData.getCheckOut(), testData.getAdditionalNeeds());

    BookingResponseModel response = step("Получить бронирование с id = " + id, () ->
            given(requestSpec)
                    .when()
                    .get("/booking/" + id)
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(BookingResponseModel.class));

    step("Проверить данные бронирования", () -> {
      assertThat(response.getFirstName(), is(testData.getFirstName()));
      assertThat(response.getLastName(), is(testData.getLastName()));
      assertThat(response.getTotalPrice(), is(testData.getTotalPrice()));
      assertThat(response.getDepositPaid(), is(testData.getDepositPaid()));
      assertThat(response.getBookingDatesResponse().getCheckIn(),
              is(testData.getCheckIn()));
      assertThat(response.getBookingDatesResponse().getCheckOut(),
              is(testData.getCheckOut()));
      assertThat(response.getAdditionalNeeds(), is(testData.getAdditionalNeeds()));
    });
  }

  @DisplayName("Неуспешное получение несуществующего бронирования")
  @Test
  void getNonExistingBookingTest() {

    TestData testData = new TestData();

    Integer id = api.createBooking(testData.getFirstName(), testData.getLastName(), testData.getTotalPrice(),
            testData.getDepositPaid(), testData.getCheckIn(), testData.getCheckOut(), testData.getAdditionalNeeds());

    api.deleteBooking(id);

    step("Проверить получение несуществующего бронирования с id = " + id, () ->
            given(requestSpec)
                    .when()
                    .get("/booking/" + id)
                    .then()
                    .spec(responseSpecStatusCode(404)));
  }

  @DisplayName("Неуспешное получение бронирования: id - строка")
  @Test
  void getIncorrectStringIdBookingTest() {

    step("Проверить получение бронирования с корректным id = abc", () ->
            given(requestSpec)
                    .when()
                    .get("/booking/" + "abc")
                    .then()
                    .spec(responseSpecStatusCode(404)));
  }

  @DisplayName("Неуспешное получение бронирования: id - отрицательное число")
  @Test
  void getIncorrectNegativeIdBookingTest() {

    step("Проверить получение бронирования с корректным id = -1", () ->
            given(requestSpec)
                    .when()
                    .get("/booking/" + -1)
                    .then()
                    .spec(responseSpecStatusCode(404)));
  }
}
