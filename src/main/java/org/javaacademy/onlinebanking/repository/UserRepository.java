package org.javaacademy.onlinebanking.repository;

import lombok.NoArgsConstructor;
import org.javaacademy.onlinebanking.entity.User;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@NoArgsConstructor
public class UserRepository {

  private final Map<String, User> users = new HashMap<>();
  private Integer pinCod = 1000;

  public String add(User user) {
    pinCod++;
    users.put(user.getPhone(), user);
    return String.valueOf(pinCod);
  }


  public List<User> findAll() {
    return new ArrayList<>(users.values());
  }

  public Optional<User> findByPhone(String phoneUser) {
    return Optional.ofNullable(users.get(phoneUser));
  }

  public User findByUuid(String uuid) {
    List<User> userList = new ArrayList<>(users.values());
    return userList.stream().filter(entity -> entity.getUuid().equals(uuid)).findFirst().orElseThrow();
  }

  public Map<String, User> getUsers() {
    return users;
  }
}
