package org.javaacademy.onlinebanking.service;

import lombok.AllArgsConstructor;
import org.javaacademy.onlinebanking.entity.Operation;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.enums.TypeOperation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BankService {
  private final DepositService depositService;
  private final OperationService operationService;
  private final UserService userService;

  public void takeMoneyFromBank(String numberDeposit, BigDecimal value, String description, String token) {
    User user = userService.findUserByToken(token);
    if (!depositService.verificationDepositAndUser(numberDeposit, user)) {
      throw new RuntimeException("This deposit doesn't belong user");
    }
    depositService.deleteMoneyByDeposit(numberDeposit, value);
    operationService.createOperation(
                new Operation(user.getUuid(),
                numberDeposit,
                TypeOperation.TYPE_OPERATION_SUB.getTypeName(),
                LocalDateTime.now(), description));
  }

  public List<Operation> paymentHistoryByUser(String token) {
    User user = userService.findUserByToken(token);
    return operationService.findAllTransactionFromAllNumberDepositsByUser(user);
  }

  public void givMoneyToBank(String numberDeposit, BigDecimal value, String description) {
    depositService.updateMoneyByDeposit(numberDeposit, value);
    User user = depositService.findByDeposit(numberDeposit).getUser();
    operationService.createOperation(new Operation(user.getUuid(),
                numberDeposit,
                TypeOperation.TYPE_OPERATION_SUB.getTypeName(),
                LocalDateTime.now(),
                description));
  }
}
