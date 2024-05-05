package org.javaacademy.onlinebanking.controller;

import lombok.AllArgsConstructor;
import org.javaacademy.onlinebanking.dto.DepositDtoRq;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.service.DepositService;
import org.javaacademy.onlinebanking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class DepositController {
  private final DepositService depositService;
  private final UserService userService;

  @GetMapping("/account")
  public ResponseEntity<List<String>> findAllDepositByUser(@RequestParam String token) {
    User user = userService.findUserByToken(token);
    List<String> allDepositByUser = depositService.findAllDepositByUser(user);
    return ResponseEntity.status(CREATED).body(allDepositByUser);
  }

  @GetMapping("/account/{account}")
  public ResponseEntity<BigDecimal> findDepositByUser(@PathVariable String account) {
    BigDecimal balance = depositService.balanceOnDeposit(account);
    return ResponseEntity.status(CREATED).body(balance);
  }

  @PostMapping("/account")
  public ResponseEntity<String> createDeposit(@RequestBody DepositDtoRq depositDtoRq) {
    User user = userService.findUserByToken(depositDtoRq.getToken());
    String account = depositService.userDepositCreate(user);
    String result = String.format("У клиента %s, номер счета: %s", user.getFio(), account);
    return ResponseEntity.status(CREATED).body(result);
  }
}
