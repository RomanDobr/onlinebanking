package org.javaacademy.onlinebanking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Deposit {
  private final String numberDeposit;
  private final User user;
  @Setter
  private BigDecimal value;

}
