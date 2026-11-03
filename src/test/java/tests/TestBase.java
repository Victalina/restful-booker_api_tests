package tests;

import com.herokuapp.restful_booker_tests.api.Api;
import com.herokuapp.restful_booker_tests.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.herokuapp.restful_booker_tests.spec.Spec.requestSpec;
import static io.restassured.RestAssured.given;

public class TestBase {

  protected final Api api = new Api();

  protected Integer id;

  @BeforeAll
  static void setUpConfig() {

    RestAssured.baseURI = TestConfig.get("base.url");

  }

  @AfterEach
  void tearDown() {

    if (id != null) {
      Response response = given(requestSpec)
              .when()
              .get("/booking/" + id)
              .then()
              .extract().response();

      if (response.getStatusCode() == 200) {
        api.deleteBooking(id);
      } else if (response.getStatusCode() != 404) {
        throw new AssertionError(
                "Unexpected status code during cleanup: " + response.getStatusCode()
        );
      }
    }
  }
}
