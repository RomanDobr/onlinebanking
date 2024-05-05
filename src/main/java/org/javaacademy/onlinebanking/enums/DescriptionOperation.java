package org.javaacademy.onlinebanking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DescriptionOperation {
  OPERATION_CASH("выдача денег по требованию на хоз.нужды"),
  OPERATION_PRODUCT("выдача денег по требованию на покупку");
  private String descriptionName;
}
