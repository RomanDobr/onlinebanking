package org.javaacademy.onlinebanking.controller;

import org.javaacademy.onlinebanking.exception.IntegrationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RestControllerAdvice
public class BusinessControllerAdvice {

  @ExceptionHandler(IntegrationException.class)
  public ResponseEntity<String> handlerIntegrationException(Exception e) {
    return ResponseEntity.status(SERVICE_UNAVAILABLE).body("Внешний сервис недоступен");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handlerException(Exception e) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("У нас произошла ошибка, уже работаем над ней.");
  }
}
