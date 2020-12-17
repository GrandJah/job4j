package ru.job4j.sell_car.environment;

import java.util.HashMap;
import java.util.Map;
import ru.job4j.sell_car.environment.interfaces.FileStorage;
import ru.job4j.sell_car.environment.interfaces.Upload;
import ru.job4j.sell_car.storage.FileUploads;
import ru.job4j.sell_car.storage.hibernate.HbmFileStorage;

public class Environment {

   private static final Environment instance = new Environment();

   public static Environment inst(Object userClass) {
      return Environment.instance;
   }

   private final Map<Class,Class> environment = new HashMap<>();

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
      this.environment.put(FileStorage.class, HbmFileStorage.class);
      this.environment.put(Upload.class, FileUploads.class);
   }


}
