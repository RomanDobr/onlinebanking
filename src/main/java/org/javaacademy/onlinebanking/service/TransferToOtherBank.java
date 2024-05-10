package org.javaacademy.onlinebanking.service;

import static org.springframework.http.HttpStatus.CREATED;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.config.BankProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransferToOtherBank {
  private final BankProperties bankProperties;
  private final RestTemplate restTemplate;

  public void transferMoneyToOtherBank(String nameBank, BigDecimal value, String description,
                                         String FIO, String numberAccount) {
    description = String.format("Из банка: %s, от %s, описание: %s", nameBank, FIO, description);
    Map<String, String> dtoRq= new HashMap<>();
    dtoRq.put("value", value.toString());
    dtoRq.put("numberAccount", numberAccount);
    dtoRq.put("description", description);
    ResponseEntity<Void> response = restTemplate
                .postForEntity(bankProperties.getPartnerUrl() + "/operations/receive", dtoRq, Void.class);
    if (response.getStatusCode() != CREATED) {
         throw new RuntimeException("Transfer Failed!");
    }
  }
}
