package org.javaacademy.onlinebanking.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
@Getter
public class AuthRepository {
  private final Map<String, String> authUsers = new HashMap<>();

  public void add(String uuid, String pinCod) {
    authUsers.put(uuid, pinCod);
  }

  public boolean checkRecordUser(String uuid, String pinCod) {
    if (authUsers.get(uuid).equals(pinCod)) {
      return true;
    }
    return false;
  }

  public List<String> findAll() {
    return new ArrayList<>(authUsers.keySet());
  }

  public String findByUuid(String uuid) {
    return authUsers.get(uuid);
  }
}
