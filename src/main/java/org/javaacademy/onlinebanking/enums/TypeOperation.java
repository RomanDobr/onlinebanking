package org.javaacademy.onlinebanking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeOperation {
  TYPE_OPERATION_SUB("списание"),
  TYPE_OPERATION_ADD("зачисление");
  private String typeName;
}
