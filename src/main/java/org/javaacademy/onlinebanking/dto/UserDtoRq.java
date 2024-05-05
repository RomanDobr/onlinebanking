package org.javaacademy.onlinebanking.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDtoRq {
  private final String numberPhone;
  private final String fio;
  private String pinCod;
}
