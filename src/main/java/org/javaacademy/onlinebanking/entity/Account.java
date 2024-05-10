package org.javaacademy.onlinebanking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Account {
  private final String numberAccount;
  private final User user;
  private BigDecimal value;

}
