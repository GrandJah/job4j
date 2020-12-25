package ru.job4j.sell_car.storage.hibernate;

import java.util.List;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.models.Advert;

public class HbmAdvStorage extends HbmStorage implements AdvStorage {
   @Override
   public List<Advert> getAll() {
      return query(sf -> sf.createQuery("from Advert", Advert.class).list());
   }

   @Override
   public Advert getById(Integer id) {
      return query(sf -> sf.get(Advert.class, id));
   }

   @Override
   public long save(Advert advert) {
      if (advert.getId() != null) {
         query(sf -> {
            sf.update(advert);
            return null;
         });
      } else {
         query(sf -> {
            sf.save(advert);
            return null;
         });
      }
      return advert.getId();
   }
}
