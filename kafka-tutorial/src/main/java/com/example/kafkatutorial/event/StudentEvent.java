package com.example.kafkatutorial.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentEvent extends BaseEvent {
  private String firstName;
  private String lastName;
}
