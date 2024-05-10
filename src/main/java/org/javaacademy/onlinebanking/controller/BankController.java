package org.javaacademy.onlinebanking.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class BankController {
  private final BankService bankService;

  @GetMapping("/bank-info")
  public ResponseEntity<String> getBankInfo() {
    return ResponseEntity.ok().body(bankService.getBakInfo());
  }
}
