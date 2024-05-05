package org.javaacademy.onlinebanking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class User {
  @NonNull
  private String fio;
  @NonNull
  private String phone;
  @NonNull
  private String uuid;
}
