package ru.job4j.sell_car.controller.stub;

import java.util.Map;
import java.util.TreeMap;
import ru.job4j.sell_car.environment.interfaces.Shadows;
import ru.job4j.sell_car.models.Shadow;
import ru.job4j.sell_car.models.User;

public class StubShadows implements Shadows {
   private static final Map<User, Shadow> SHADOW_MAP = new TreeMap<>((o1, o2) -> {
      if ((o1 == null && o2 == null)) {
         return 0;
      }
      if (o2 == null) {
         return -1;
      }
      if (o1 == null || o1.getId() == null) {
         return 1;
      }
      if (o2.getId() == null) {
         return -1;
      }
      return o1.getId() - o2.getId();
   });

   @Override
   public Shadow findByUser(User user) {
      return SHADOW_MAP.get(user);
   }

   @Override
   public Shadow update(Shadow shadow) {
      SHADOW_MAP.put(shadow.getUser(), shadow);
      return shadow;
   }

   @Override
   public void save(Shadow shadow) {
      SHADOW_MAP.put(shadow.getUser(), shadow);
      new StubUserStorage().save(shadow.getUser());
   }

   @Override
   public User findByToken(String token) {
      return SHADOW_MAP.entrySet().stream()
                .filter(e->e.getValue().getToken().equals(token))
                .map(Map.Entry::getKey).findFirst().orElse(null);
   }
}
