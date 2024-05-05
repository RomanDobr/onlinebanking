package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private static final String PREFIX = "online";
  private static final String POSTFIX = "token";
  private final UserRepository userRepository;
  private final AuthService authService;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public String createUser(String userFIO, String numberPhone) {
    if (userRepository.findByPhone(numberPhone).isPresent()) {
      throw new RuntimeException("User is exist");
    }
    String uuid = UUID.randomUUID().toString();
    String pinCod = userRepository.add(new User(userFIO, numberPhone, uuid));
    authService.createRecordUser(uuid, pinCod);
    return pinCod;
  }

  public String findTokenByPhone(String numberPhone) {
    if (userRepository.findByPhone(numberPhone).isEmpty()) {
      throw new RuntimeException("Not exist user");
    }
    User user = userRepository.findByPhone(numberPhone).orElseThrow();
    String uuidUser = user.getUuid();
    if (!authService.checkAuthRecordUser(uuidUser, authService.getPinCod(uuidUser))) {
      throw new RuntimeException("Record this phone not exist");
    }
    return createToken(uuidUser);
  }

  public User findUserByToken(String token) {
    if (token == null) {
      throw new RuntimeException("Token not exist");
    }
    String uuid = token.trim().substring(PREFIX.length(), token.length() - POSTFIX.length());
    return userRepository.findByUuid(uuid);
  }

  private String createToken(String uuid) {
    return PREFIX + uuid + POSTFIX;
  }
}
