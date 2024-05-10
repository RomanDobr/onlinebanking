package org.javaacademy.onlinebanking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bank")
@Getter
@Setter
public class BankProperties {
  private String name;
  private String partnerUrl;
}
