package com.herokuapp.restful_booker_tests.utils;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class RandomUtils {

  Faker faker = new Faker();

  public String getRandomFirstName() {

    return faker.name().firstName();
  }

  public String getRandomLastName() {

    return faker.name().lastName();
  }

  public Integer getRandomTotalPrice() {

    return faker.number().numberBetween(0, 1000000);
  }

  public Boolean getRandomDepositPaid() {

    return ThreadLocalRandom.current().nextBoolean();
  }

  public String getRandomCheckIn() {

    Date fakerDate = faker.date().past(3650, TimeUnit.DAYS);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(fakerDate);
  }

  public String getRandomCheckOut(String checkIn, int numberOfDays) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
    LocalDate checkOutDate = checkInDate.plusDays(numberOfDays);

    return checkOutDate.format(formatter);
  }

  public String getRandomAdditionalNeeds() {

    String[] additionalNeeds = {"Breakfast", "Parking", "Air conditioner", " - ", ""};
    return faker.options().option(additionalNeeds);
  }
}
