package org.javaacademy.onlinebanking.repository;

import lombok.NoArgsConstructor;
import org.javaacademy.onlinebanking.entity.Deposit;
import org.javaacademy.onlinebanking.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@NoArgsConstructor
public class DepositRepository {
  private final Map<String, Deposit> deposits = new HashMap<>();

  public void addNumberDeposit(String numberDeposit, Deposit deposit) {
    depositCheckNumber(numberDeposit);
    deposits.put(numberDeposit, deposit);
  }

  public Deposit findByDeposit(String numberDeposit) {
    depositCheckNumber(numberDeposit);
    return deposits.get(numberDeposit);
  }

  public List<String> findAllNumbersByUser(User user) {
    return new ArrayList<>(deposits.values())
                .stream()
                .filter(deposit -> deposit.getUser().equals(user))
                .map(Deposit::getNumberDeposit)
                .toList();
  }

  public void updateMoneyByDeposit(String numberDeposit, BigDecimal value) {
    depositCheckNumber(numberDeposit);
    Deposit deposit = deposits.get(numberDeposit);
    deposit.setValue(deposit.getValue().add(value));
    deposits.put(numberDeposit, deposit);
  }

  public void deleteMoneyByDeposit(String numberDeposit, BigDecimal value) {
    depositCheckNumber(numberDeposit);
    Deposit deposit = deposits.get(numberDeposit);
    if (deposit.getValue().compareTo(value) > 0) {
      deposit.setValue(deposit.getValue().subtract(value));
      deposits.put(numberDeposit, deposit);
    } else {
      deposit.setValue(BigDecimal.ZERO);
      deposits.put(numberDeposit, deposit);
    }
  }

  public boolean verificationDepositAndUser(String numberDeposit, User user) {
    depositCheckNumber(numberDeposit);
    return Objects.equals(deposits.get(numberDeposit).getUser(), user);
  }

  private void depositCheckNumber(String numberDeposit) {
    if (!deposits.containsKey(numberDeposit) && !deposits.isEmpty()) {
      throw new RuntimeException("Deposit not exist");
    }
  }
}
