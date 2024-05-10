package org.javaacademy.onlinebanking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTmplConfig {
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
