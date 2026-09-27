package com.herokuapp.restful_booker_tests.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestModel {

  private String username;
  private String password;

}
