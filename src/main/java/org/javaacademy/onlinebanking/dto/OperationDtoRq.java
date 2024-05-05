package org.javaacademy.onlinebanking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDtoRq {
  private String token;
  private BigDecimal value;
  private String numberDeposit;
  private String description;
}
