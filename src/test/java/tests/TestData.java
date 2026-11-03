package tests;

import com.herokuapp.restful_booker_tests.utils.RandomUtils;
import lombok.Getter;

@Getter
public class TestData {

  private final RandomUtils randomUtils = new RandomUtils();

  private final String firstName = randomUtils.getRandomFirstName();
  private final String lastName = randomUtils.getRandomLastName();
  private final Integer totalPrice = randomUtils.getRandomTotalPrice();
  private final Boolean depositPaid = randomUtils.getRandomDepositPaid();
  private final String checkIn = randomUtils.getRandomCheckIn();
  private final String checkOut = randomUtils.getRandomCheckOut(checkIn, 20);
  private final String additionalNeeds = randomUtils.getRandomAdditionalNeeds();
}
