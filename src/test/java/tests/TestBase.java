package tests;

import com.herokuapp.restful_booker_tests.config.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

  @BeforeAll
  static void setUpConfig() {

    RestAssured.baseURI = TestConfig.get("base.url");

  }
}
