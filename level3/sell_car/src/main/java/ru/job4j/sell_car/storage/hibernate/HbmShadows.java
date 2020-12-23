package ru.job4j.sell_car.storage.hibernate;

import ru.job4j.sell_car.environment.interfaces.Shadows;
import ru.job4j.sell_car.models.Shadow;
import ru.job4j.sell_car.models.User;

public class HbmShadows extends HbmStorage implements Shadows {

   @Override
   public Shadow findByUser(User user) {
      return null;
   }

   @Override
   public Shadow update(Shadow shadow) {
      return null;
   }

   @Override
   public void save(Shadow shadow) {
   }
}
