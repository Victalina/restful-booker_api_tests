package tests;

import com.herokuapp.restful_booker_tests.utils.RandomUtils;
import lombok.Data;

@Data
public class TestData {

  private RandomUtils randomUtils = new RandomUtils();

  private String firstName = randomUtils.getRandomFirstName();
  private String lastName = randomUtils.getRandomLastName();
  private Integer totalPrice = randomUtils.getRandomTotalPrice();
  private Boolean depositPaid = randomUtils.getRandomDepositPaid();
  private String checkIn = randomUtils.getRandomCheckIn();
  private String checkOut = randomUtils.getRandomCheckOut(checkIn, 20);
  private String additionalNeeds = randomUtils.getRandomAdditionalNeeds();
}
