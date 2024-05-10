package org.javaacademy.onlinebanking.controller;

import lombok.AllArgsConstructor;
import org.javaacademy.onlinebanking.dto.OperationDtoRq;
import org.javaacademy.onlinebanking.dto.OperationDtoRs;
import org.javaacademy.onlinebanking.entity.Operation;
import org.javaacademy.onlinebanking.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class AccountOperationController {
  private final BankService bankService;

  @GetMapping(value = "/operations", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OperationDtoRs>> findAllOperationsByUser(@RequestParam String token) {
    List<OperationDtoRs> operationDtoRs = bankService
            .paymentHistoryByUser(token)
            .stream()
            .map(this::convertOperationToOperationDtoRs)
            .toList();
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(operationDtoRs);
  }

  @PostMapping(value = "/operations/pay", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> takeMoneyFromAccountByUser(@RequestBody OperationDtoRq operationDtoRq) {
    bankService.takeMoneyFromBank(operationDtoRq.getNumberAccount(),
                operationDtoRq.getValue(),
                operationDtoRq.getDescription(),
                operationDtoRq.getToken());
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/operations/receive", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> giveMoneyToBankByUser(@RequestBody OperationDtoRq operationDtoRq) {
    bankService.giveMoneyToBank(operationDtoRq.getNumberAccount(),
                operationDtoRq.getValue(),
                operationDtoRq.getDescription());
    return ResponseEntity.ok().build();
  }

  private OperationDtoRs convertOperationToOperationDtoRs(Operation operation) {
    return new OperationDtoRs(operation.getUuid(),
                              operation.getNumberAccount(),
                              operation.getOperation(),
                              operation.getDate(),
                              operation.getDescription());
  }
}
