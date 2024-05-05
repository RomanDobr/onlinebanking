package org.javaacademy.onlinebanking.controller;

import lombok.AllArgsConstructor;
import org.javaacademy.onlinebanking.dto.OperationDtoRq;
import org.javaacademy.onlinebanking.entity.Operation;
import org.javaacademy.onlinebanking.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class BusinessOperationController {
  private final BankService bankService;

  @GetMapping("/operations")
  public ResponseEntity<List<Operation>> findAllOperationsByUser(@RequestParam String token) {
    List<Operation> operations = bankService.paymentHistoryByUser(token);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(operations);
  }

  @PostMapping("/operations/pay")
  public ResponseEntity<?> takeMoneyFromDepositByUser(@RequestBody OperationDtoRq operationDtoRq) {
    bankService.takeMoneyFromBank(operationDtoRq.getNumberDeposit(),
                operationDtoRq.getValue(),
                operationDtoRq.getDescription(),
                operationDtoRq.getToken());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/operations/receive")
  public ResponseEntity<?> givMoneyToBankByUser(@RequestBody OperationDtoRq operationDtoRq) {
    bankService.givMoneyToBank(operationDtoRq.getNumberDeposit(),
                operationDtoRq.getValue(),
                operationDtoRq.getDescription());
    return ResponseEntity.ok().build();
  }
}
