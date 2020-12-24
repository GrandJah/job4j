package ru.job4j.sell_car.controller.stub;

import java.util.HashMap;
import java.util.Map;
import ru.job4j.sell_car.environment.interfaces.UserStorage;
import ru.job4j.sell_car.models.User;

public class StubUserStorage implements UserStorage {
   private static final Map<String, User> USER_MAP = new HashMap<>();
   private static int genId = 77;

   @Override
   public User findByName(String name) {
      return USER_MAP.get(name);
   }

   @Override
   public User save(User user) {
      Integer id = user.getId();
      if (id == null) {
         id = genId++;
         user.setId(id);
         USER_MAP.put(user.getName(), user);
      }
      return user;
   }
}
