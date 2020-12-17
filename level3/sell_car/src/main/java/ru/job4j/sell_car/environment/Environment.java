package ru.job4j.sell_car.environment;

import java.util.HashMap;
import java.util.Map;
import ru.job4j.sell_car.environment.interfaces.AdvStorage;
import ru.job4j.sell_car.environment.interfaces.CarStorage;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.environment.interfaces.Shadows;
import ru.job4j.sell_car.environment.interfaces.Upload;
import ru.job4j.sell_car.environment.interfaces.UserStorage;
import ru.job4j.sell_car.storage.FileUploads;
import ru.job4j.sell_car.storage.hibernate.HbmAdvStorage;
import ru.job4j.sell_car.storage.hibernate.HbmCarStorage;
import ru.job4j.sell_car.storage.hibernate.HbmFileStorage;
import ru.job4j.sell_car.storage.hibernate.HbmShadows;
import ru.job4j.sell_car.storage.hibernate.HbmUserStorage;

public class Environment {

   private static final Environment INSTANCE = new Environment();

   public static Environment inst(Object userClass) {
      return Environment.INSTANCE;
   }

   private final Map<Class, Class> environment = new HashMap<>();

   public <T> T get(Class<T> clazz) {
      T instance = null;
      try {
         instance = (T) environment.get(clazz).newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
         e.printStackTrace();
      }
      return instance;
   }

   Environment() {
      this.environment.put(Shadows.class, HbmShadows.class);
      this.environment.put(UserStorage.class, HbmUserStorage.class);
      this.environment.put(CarStorage.class, HbmCarStorage.class);
      this.environment.put(AdvStorage.class, HbmAdvStorage.class);
      this.environment.put(FileStorage.class, HbmFileStorage.class);
      this.environment.put(Upload.class, FileUploads.class);
   }


}
