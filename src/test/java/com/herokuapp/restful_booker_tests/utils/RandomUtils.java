package com.herokuapp.restful_booker_tests.utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

  private final Faker faker = new Faker();

  public String getRandomFirstName() {

    return faker.name().firstName();
  }

  public String getRandomLastName() {

    return faker.name().lastName();
  }

  public Integer getRandomTotalPrice() {

    return faker.number().numberBetween(1, 1000000);
  }

  public Boolean getRandomDepositPaid() {

    return ThreadLocalRandom.current().nextBoolean();
  }

  public String getRandomCheckIn() {

    LocalDate checkIn = LocalDate.now()
            .plusDays(ThreadLocalRandom.current().nextInt(1, 366));

    return checkIn.toString();
  }

  public String getRandomCheckOut(String checkIn, int numberOfDays) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
    LocalDate checkOutDate = checkInDate.plusDays(numberOfDays);

    return checkOutDate.format(formatter);
  }

  public String getRandomAdditionalNeeds() {

    String[] additionalNeeds = {"Breakfast", "Parking", "Air conditioner", ""};
    return faker.options().option(additionalNeeds);
  }
}
