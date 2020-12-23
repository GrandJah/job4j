package ru.job4j.sell_car.storage.hibernate;

import ru.job4j.sell_car.environment.interfaces.UserStorage;
import ru.job4j.sell_car.models.User;

public class HbmUserStorage extends HbmStorage implements UserStorage {

   @Override
   public User findByName(String user) {
      return null;
   }

   @Override
   public User save(User of) {
      return null;
   }
}
