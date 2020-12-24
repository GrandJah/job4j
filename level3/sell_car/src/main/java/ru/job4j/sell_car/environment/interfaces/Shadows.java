package ru.job4j.sell_car.environment.interfaces;

import ru.job4j.sell_car.models.Shadow;
import ru.job4j.sell_car.models.User;

public interface Shadows {

   Shadow findByUser(User user);

   default boolean checkPass(User user, String pass) {
      return findByUser(user).checkPass(pass);
   }

   default String createToken(User user) {
      String token = Shadow.createToken(user);
      Shadow shadow = findByUser(user);
      shadow.setToken(token);
      update(shadow);
      return token;
   }

   Shadow update(Shadow shadow);

   void save(Shadow shadow);

   User findByToken(String token);
}
