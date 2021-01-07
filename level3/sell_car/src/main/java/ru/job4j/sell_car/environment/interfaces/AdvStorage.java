package ru.job4j.sell_car.environment.interfaces;

import java.util.List;
import java.util.Map;
import ru.job4j.sell_car.models.Advert;

public interface AdvStorage {
   List<Advert> getAll();

   Advert getById(Integer id);

   long save(Advert adv);

   default List<Advert> getAllWithFilter(Map<String, Object> filters) {
      return getAll();
   }
}
