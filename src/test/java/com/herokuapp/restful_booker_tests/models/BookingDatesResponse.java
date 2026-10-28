package com.herokuapp.restful_booker_tests.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookingDatesResponse {

  @JsonProperty("checkin")
  private String checkIn;

  @JsonProperty("checkout")
  private String checkOut;

}
