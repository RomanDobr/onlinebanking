package org.javaacademy.onlinebanking.controller;

import static org.springframework.http.HttpStatus.CREATED;
import lombok.AllArgsConstructor;
import org.javaacademy.onlinebanking.dto.AccountDtoRq;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.service.AccountService;
import org.javaacademy.onlinebanking.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class AccountController {
  private final AccountService accountService;
  private final UserService userService;

  @GetMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<String>> findAllAccountByUser(@RequestParam String token) {
    User user = userService.findUserByToken(token);
    List<String> allAccountByUser = accountService.findAllAccountByUser(user);
    return ResponseEntity
            .status(CREATED)
            .body(allAccountByUser);
  }

  @GetMapping(value = "/account/{account}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BigDecimal> findAccountByUser(@PathVariable String account) {
    BigDecimal balance = accountService.balanceOnAccount(account);
    return ResponseEntity
            .status(CREATED)
            .body(balance);
  }

  @PostMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createAccount(@RequestBody AccountDtoRq accountDtoRq) {
    User user = userService.findUserByToken(accountDtoRq.getToken());
    return ResponseEntity
            .status(CREATED)
            .body(accountService
            .userAccountCreate(user));
  }
}
