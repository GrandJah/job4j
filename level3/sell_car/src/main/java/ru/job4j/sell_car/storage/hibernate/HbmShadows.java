package ru.job4j.sell_car.storage.hibernate;

import javax.persistence.NoResultException;
import ru.job4j.sell_car.environment.interfaces.Shadows;
import ru.job4j.sell_car.models.Shadow;
import ru.job4j.sell_car.models.User;

public class HbmShadows extends HbmStorage implements Shadows {

   @Override
   public Shadow findByUser(User user) {
      try {
         return query(sf -> sf.createQuery("from Shadow where user =:user", Shadow.class)
                              .setParameter("user", user).getSingleResult());
      } catch (NoResultException e) {
         return null;
      }
   }

   @Override
   public Shadow update(Shadow shadow) {
      query(sf -> {
         sf.update(shadow);
         return null;
      });
      return shadow;
   }

   @Override
   public void save(Shadow shadow) {
      if (shadow.getId() != null) {
         query(sf -> {
            sf.update(shadow);
            return null;
         });
      } else {
         query(sf -> {
            sf.save(shadow);
            return null;
         });
      }
   }

   @Override
   public User findByToken(String token) {
      try {
         return query(sf -> sf.createQuery("from Shadow where token =:token", Shadow.class)
                              .setParameter("token", token).getSingleResult()).getUser();
      } catch (NoResultException e) {
         return null;
      }

   }
}
