package org.javaacademy.onlinebanking.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.javaacademy.onlinebanking.entity.Account;
import org.javaacademy.onlinebanking.entity.User;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.*;

@Component
@NoArgsConstructor
public class AccountRepository {
  @Getter
  private final Map<String, Account> accounts = new HashMap<>();

  public void addNumberAccount(String numberAccount, Account account) {
    accountCheckNumber(numberAccount);
    accounts.put(numberAccount, account);
  }

  public Account findByAccount(String numberAccount) {
    accountCheckNumber(numberAccount);
    return accounts.get(numberAccount);
  }

  public List<String> findAllNumbersByUser(User user) {
    return new ArrayList<>(accounts.values())
                .stream()
                .filter(account -> account.getUser().equals(user))
                .map(Account::getNumberAccount)
                .toList();
  }

  public void updateMoneyByAccount(String numberAccount, BigDecimal value) {
    accountCheckNumber(numberAccount);
    Account account = accounts.get(numberAccount);
    account.setValue(account.getValue().add(value));
    accounts.put(numberAccount, account);
  }

  public void deleteMoneyByAccount(String numberAccount, BigDecimal value) {
    accountCheckNumber(numberAccount);
    Account account = accounts.get(numberAccount);
    if (account.getValue().compareTo(value) > 0) {
      account.setValue(account.getValue().subtract(value));
      accounts.put(numberAccount, account);
    } else {
      account.setValue(BigDecimal.ZERO);
      accounts.put(numberAccount, account);
    }
  }

  public boolean verificationAccountAndUser(String numberAccount, User user) {
    accountCheckNumber(numberAccount);
    return Objects.equals(accounts.get(numberAccount).getUser(), user);
  }

  private void accountCheckNumber(String numberAccount) {
    if (!accounts.containsKey(numberAccount) && !accounts.isEmpty()) {
      throw new RuntimeException("Deposit not exist");
    }
  }
}
