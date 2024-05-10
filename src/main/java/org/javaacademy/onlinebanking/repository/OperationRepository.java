package org.javaacademy.onlinebanking.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.entity.Operation;
import org.javaacademy.onlinebanking.entity.User;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OperationRepository {
  private final AccountRepository accountRepository;
  private final Map<String, List<Operation>> transactions = new HashMap<>();

  public List<Operation> findAllTransactionByDeposit(String numberDeposit) {
    return transactions.get(numberDeposit)
                .stream()
                .sorted(Comparator.comparing(Operation::getDate))
                .toList();
  }

  public void addOperation(Operation operation) {
    if (!transactions.isEmpty()) {
      List<Operation> operations = transactions.get(operation.getNumberAccount());
      operations.add(operation);
      transactions.put(operation.getNumberAccount(), operations);
    } else {
      List<Operation> operations = new ArrayList<>();
      operations.add(operation);
      transactions.put(operation.getNumberAccount(), operations);
    }
  }

  public Map<String, List<Operation>> getTransactions() {
    return transactions;
  }

  public List<Operation> findAllTransactionFromAllNumberDepositsByUser(User user) {
    return accountRepository.findAllNumbersByUser(user)
               .stream()
               .flatMap(numberDeposit -> transactions.get(numberDeposit).stream()
               .sorted(Comparator.comparing(Operation::getDate)))
               .collect(Collectors.toList());
  }
}
