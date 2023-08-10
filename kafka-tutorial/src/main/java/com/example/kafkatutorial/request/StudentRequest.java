package com.example.kafkatutorial.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentRequest {
  private Long id;
  private String firstName;
  private String lastName;
}
