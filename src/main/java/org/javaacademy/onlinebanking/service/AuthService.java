package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthRepository authRepository;

  public void createRecordUser(String uuid, String pinCod) {
    authRepository.add(uuid, pinCod);
  }

  public String getPinCod(String uuid) {
    return authRepository.findByUuid(uuid);
  }

  public boolean checkAuthRecordUser(String uuid, String pinCod) {
    return authRepository.checkRecordUser(uuid, pinCod);
  }

  public List<String> findAll() {
    return authRepository.findAll();
  }
}
