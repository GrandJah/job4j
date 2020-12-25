package ru.job4j.sell_car.storage.hibernate;

import javax.persistence.NoResultException;
import ru.job4j.sell_car.environment.interfaces.UserStorage;
import ru.job4j.sell_car.models.User;

public class HbmUserStorage extends HbmStorage implements UserStorage {

   @Override
   public User findByName(String name) {
      try {
         return query(
          sf -> sf.createQuery("from User where name =:name", User.class).setParameter("name", name)
                  .getSingleResult());
      } catch (NoResultException e) {
         return null;
      }
   }

   @Override
   public User save(User user) {
      if (user.getId() != null) {
         query(sf -> {
            sf.update(user);
            return null;
         });
      } else {
         query(sf -> {
            sf.save(user);
            return null;
         });
      }
      return user;
   }
}
