package org.javaacademy.onlinebanking.service;

import lombok.AllArgsConstructor;
import org.javaacademy.onlinebanking.entity.Operation;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.OperationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class OperationService {
  private final OperationRepository operationRepository;

  public void createOperation(Operation operation) {
    operationRepository.addOperation(operation);
  }

  public List<Operation> findAllTransactionByDeposit(String numberDeposit) {
    return operationRepository.findAllTransactionByDeposit(numberDeposit);
  }

  public List<Operation> findAllTransactionFromAllNumberAccountsByUser(User user) {
    return operationRepository.findAllTransactionFromAllNumberDepositsByUser(user);
  }
}
