package org.javaacademy.onlinebanking.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Operation {
  private final String uuid;
  private final String numberAccount;
  private final String operation;
  private final LocalDateTime date;
  private final String description;
}
