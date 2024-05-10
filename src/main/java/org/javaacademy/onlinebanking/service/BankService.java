package org.javaacademy.onlinebanking.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.config.BankProperties;
import org.javaacademy.onlinebanking.entity.Operation;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.enums.TypeOperation;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.javaacademy.onlinebanking.enums.TypeOperation.TYPE_OPERATION_SUB;

@Service
@RequiredArgsConstructor
public class BankService {
  private final AccountService accountService;
  private final OperationService operationService;
  private final UserService userService;
  private final BankProperties bankProperties;
  private final TransferToOtherBank transfersToOtherBank;
  private final AuthService authService;

  public void takeMoneyFromBank(String numberAccount, BigDecimal value, String description, String token) {
    User user = userService.findUserByToken(token);
    if (!accountService.verificationAccountAndUser(numberAccount, user)) {
      throw new RuntimeException("This deposit doesn't belong user");
    }
    accountService.deleteMoneyByAccount(numberAccount, value);
    operationService.createOperation(
                new Operation(user.getUuid(),
                numberAccount,
                TYPE_OPERATION_SUB.getTypeName(),
                LocalDateTime.now(), description));
  }

  public List<Operation> paymentHistoryByUser(String token) {
    User user = userService.findUserByToken(token);
    return operationService.findAllTransactionFromAllNumberAccountsByUser(user);
  }

  public void giveMoneyToBank(String numberAccount, BigDecimal value, String description) {
    accountService.updateMoneyByAccount(numberAccount, value);
    User user = accountService.findByAccount(numberAccount).getUser();
    operationService.createOperation(new Operation(user.getUuid(),
                numberAccount,
                TYPE_OPERATION_SUB.getTypeName(),
                LocalDateTime.now(),
                description));
  }

  public String getBakInfo() {
    return bankProperties.getName();
  }

  public void transferToOtherBank(String token, BigDecimal value, String description,
                                  String numberAccount, String numberAccountToSend) {
    User user = userService.findUserByToken(token);
    BigDecimal balance = accountService.balanceOnAccount(numberAccount);
    if (balance.compareTo(value) >= 0) {
      transfersToOtherBank.transferMoneyToOtherBank(
              bankProperties.getName(),
              value,
              description,
              user.getFio(),
              numberAccountToSend
      );
    accountService.deleteMoneyByAccount(numberAccount, value);
    operationService.createOperation( new Operation(
              user.getUuid(),
              numberAccount,
              TYPE_OPERATION_SUB.getTypeName(),
              LocalDateTime.now(),
              description));
    }
  }
}
