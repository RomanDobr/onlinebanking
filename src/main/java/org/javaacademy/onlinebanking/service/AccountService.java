package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.entity.Account;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
  private Integer numberAccount = 100000;
  private final AccountRepository accountRepository;

  public String userAccountCreate(User user) {
    Account account = new Account(createNumberAccount(), user, BigDecimal.ZERO);
    accountRepository.addNumberAccount(account.getNumberAccount(), account);
    return account.getNumberAccount();
  }

  public Account findByAccount(String numberAccount) {
    return accountRepository.findByAccount(numberAccount);
  }

  public void updateMoneyByAccount(String numberAccount, BigDecimal value) {
    accountRepository.updateMoneyByAccount(numberAccount, value);
  }

  public void deleteMoneyByAccount(String numberAccount, BigDecimal value) {
    accountRepository.deleteMoneyByAccount(numberAccount, value);
  }

  public List<String> findAllAccountByUser(User user) {
    return accountRepository.findAllNumbersByUser(user);
  }

  public BigDecimal balanceOnAccount(String numberAccount) {
    return accountRepository.findByAccount(numberAccount).getValue();
  }

  public boolean verificationAccountAndUser(String numberAccount, User user) {
    return accountRepository.verificationAccountAndUser(numberAccount, user);
  }

  private String createNumberAccount() {
    return Integer.toString(numberAccount++);
  }
}
