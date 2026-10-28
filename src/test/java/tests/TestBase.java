package tests;

import com.herokuapp.restful_booker_tests.api.Api;
import com.herokuapp.restful_booker_tests.config.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

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
      api.deleteBooking(id);
    }
  }
}
