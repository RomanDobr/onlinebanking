package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.entity.Deposit;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.DepositRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositService {
  private Integer numberDeposit = 100000;
  private final DepositRepository depositRepository;

  public String userDepositCreate(User user) {
    Deposit deposit = new Deposit(createNumberDeposit(), user, new BigDecimal("0.0"));
    depositRepository.addNumberDeposit(deposit.getNumberDeposit(), deposit);
    return deposit.getNumberDeposit();
  }

  public Deposit findByDeposit(String numberDeposit) {
    return depositRepository.findByDeposit(numberDeposit);
  }

  public void updateMoneyByDeposit(String numberDeposit, BigDecimal value) {
    depositRepository.updateMoneyByDeposit(numberDeposit, value);
  }

  public void deleteMoneyByDeposit(String numberDeposit, BigDecimal value) {
    depositRepository.deleteMoneyByDeposit(numberDeposit, value);
  }

  public List<String> findAllDepositByUser(User user) {
    return depositRepository.findAllNumbersByUser(user);
  }

  public BigDecimal balanceOnDeposit(String numberDeposit) {
    return depositRepository.findByDeposit(numberDeposit).getValue();
  }

  public boolean verificationDepositAndUser(String numberDeposit, User user) {
    return depositRepository.verificationDepositAndUser(numberDeposit, user);
  }

  private String createNumberDeposit() {
    return Integer.toString(numberDeposit++);
  }
}
