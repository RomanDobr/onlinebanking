package org.javaacademy.onlinebanking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.javaacademy.onlinebanking.dto.UserDtoRq;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.service.AuthService;
import org.javaacademy.onlinebanking.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/bank/auth")
@AllArgsConstructor
@Tag(name = "Регистрация, авторизация")
public class AuthController {
  private final AuthService authService;
  private final UserService userService;

  @Operation(summary = "Регистрация")
  @PostMapping(value = "/user/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> signUp(@RequestBody UserDtoRq userDtoRq) {
    String pinCod = userService.createUser(userDtoRq.getFio(), userDtoRq.getNumberPhone());
    return ResponseEntity.status(CREATED).body(pinCod);
  }

  @Operation(summary = "Авторизация")
  @PostMapping(value = "/user/auth", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> signIn(@RequestBody UserDtoRq userDtoRq) {
    String token = userService.findTokenByPhone(userDtoRq.getNumberPhone());
    User user = userService.findUserByToken(token);
    if (!authService.checkAuthRecordUser(user.getUuid(), userDtoRq.getPinCod())) {
      return ResponseEntity.status(FORBIDDEN).body("Pin Cod is fault");
    }
    authService.createRecordUser(user.getUuid(), userDtoRq.getPinCod());
    return ResponseEntity.status(ACCEPTED).body(token);
  }
}
