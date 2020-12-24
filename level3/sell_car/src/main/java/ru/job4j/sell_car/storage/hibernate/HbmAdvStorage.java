package ru.job4j.sell_car.storage.hibernate;

import java.util.List;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.models.Advert;

public class HbmAdvStorage extends HbmStorage implements AdvStorage {
   @Override
   public List<Advert> getAll() {
      return null;
   }

   @Override
   public Advert getById(Integer id) {
      return null;
   }

   @Override
   public long save(Advert adv) {
      return 0;
   }
}
