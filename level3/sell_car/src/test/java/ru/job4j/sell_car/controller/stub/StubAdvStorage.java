package ru.job4j.sell_car.controller.stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.models.Advert;

public class StubAdvStorage implements AdvStorage {
   private static final Map<Integer, Advert> ADVERTS = new HashMap<>();
   private static int genId = 77;

   @Override
   public List<Advert> getAll() {
      return new ArrayList<>(ADVERTS.values());
   }

   @Override
   public Advert getById(Integer id) {
      return ADVERTS.get(id);
   }

   @Override
   public long save(Advert adv) {
      Integer id = adv.getId();
      if (id == null) {
         id = genId++;
         adv.setId(id);
      }
      ADVERTS.put(id, adv);
      return id;
   }
}
