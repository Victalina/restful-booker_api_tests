package com.herokuapp.restful_booker_tests.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingDatesRequest {

  @JsonProperty("checkin")
  private String checkIn;

  @JsonProperty("checkout")
  private String checkOut;

}
