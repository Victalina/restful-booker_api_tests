package tests;

import com.herokuapp.restful_booker_tests.config.TestConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.herokuapp.restful_booker_tests.spec.Spec.requestSpec;
import static com.herokuapp.restful_booker_tests.spec.Spec.responseSpecStatusCode;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

@DisplayName("Удаление бронирования")
public class DeleteBookingTests extends TestBase {

  @DisplayName("Успешное удаление бронирования с auth токеном")
  @Test
  void successfulDeleteBookingWithTokenTest() {

    TestData testData = new TestData();

    id = api.createBooking(testData.getFirstName(), testData.getLastName(), testData.getTotalPrice(),
            testData.getDepositPaid(), testData.getCheckIn(), testData.getCheckOut(), testData.getAdditionalNeeds());

    String token = api.createToken(TestConfig.get("admin.login"), TestConfig.get("admin.password"));

    step("Удалить бронирование с id = " + id + " с auth токеном", () -> given(requestSpec)
            .header("Cookie", "token=" + token)
            .when()
            .delete("/booking/" + id)
            .then()
            .spec(responseSpecStatusCode(201)));

    api.getNonExistentBooking(id);
  }

  @DisplayName("Успешное удаление бронирования с basic auth")
  @Test
  void successfulDeleteBookingWithBasicAuthTest() {

    TestData testData = new TestData();

    id = api.createBooking(testData.getFirstName(), testData.getLastName(), testData.getTotalPrice(),
            testData.getDepositPaid(), testData.getCheckIn(), testData.getCheckOut(), testData.getAdditionalNeeds());

    step("Удалить бронирование с id = " + id + " с basic auth", () ->
            given(requestSpec)
                    .auth().preemptive().basic(TestConfig.get("admin.login"), TestConfig.get("admin.password"))
                    .when()
                    .delete("/booking/" + id)
                    .then()
                    .spec(responseSpecStatusCode(201)));

    api.getNonExistentBooking(id);
  }

  @Disabled("BUG004: DELETE возвращает 405 вместо ожидаемого 404 при удалении несуществующего бронирования")
  @DisplayName("Неуспешное удаление несуществующего бронирования")
  @Test
  void unsuccessfulDeleteNonExistentBookingTest() {
    TestData testData = new TestData();

    id = api.createBooking(testData.getFirstName(), testData.getLastName(), testData.getTotalPrice(),
            testData.getDepositPaid(), testData.getCheckIn(), testData.getCheckOut(), testData.getAdditionalNeeds());

    api.deleteBooking(id);

    String token = api.createToken(TestConfig.get("admin.login"), TestConfig.get("admin.password"));

    step("Удалить несуществующее бронирование id = " + id, () ->
            given(requestSpec)
                    .header("Cookie", "token=" + token)
                    .when()
                    .delete("/booking/" + id)
                    .then()
                    .spec(responseSpecStatusCode(404)));
  }

  @DisplayName("Неуспешное удаление бронирования без auth токена и basic auth")
  @Test
  void unsuccessfulDeleteBookingWithoutAuthTest() {

    TestData testData = new TestData();

    id = api.createBooking(testData.getFirstName(), testData.getLastName(), testData.getTotalPrice(),
            testData.getDepositPaid(), testData.getCheckIn(), testData.getCheckOut(), testData.getAdditionalNeeds());

    step("Удалить бронирование id = " + id + " без auth токена и basic auth", () ->
            given(requestSpec)
                    .when()
                    .delete("/booking/" + id)
                    .then()
                    .spec(responseSpecStatusCode(403)));
  }
}
