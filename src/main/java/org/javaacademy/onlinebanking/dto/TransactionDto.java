package org.javaacademy.onlinebanking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
  private List<OperationDtoRs> operationDtoRs;
}
