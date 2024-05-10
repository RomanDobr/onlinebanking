package org.javaacademy.onlinebanking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDtoRs {
  private String uuid;
  private String numberAccount;
  private String operation;
  private LocalDateTime date;
  private String description;
}
