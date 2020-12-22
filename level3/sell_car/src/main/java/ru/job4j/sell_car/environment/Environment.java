package ru.job4j.sell_car.environment;

import org.apache.log4j.Logger;

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
   private static final Logger LOG = Logger.getLogger(Environment.class);

   private static Environment instance;

   public static Environment inst(Object userClass) {
      LOG.debug(String.format("Instance for %s", userClass));
      if (instance == null) {
         LOG.info("load default environment");
         instance = new Environment();
         instance.changeEnvironment(DEFAULT_ENVIRONMENT);
      }
      return Environment.instance;
   }

   private final Map<Class, Class> environment = new HashMap<>();

   protected static final Map<Class, Class> DEFAULT_ENVIRONMENT = new HashMap<Class, Class>() {
      {
         put(Shadows.class, HbmShadows.class);
         put(UserStorage.class, HbmUserStorage.class);
         put(CarStorage.class, HbmCarStorage.class);
         put(AdvStorage.class, HbmAdvStorage.class);
         put(FileStorage.class, HbmFileStorage.class);
         put(Upload.class, FileUploads.class);
      }
   };

   public <T> T get(Class<T> interfaceClazz) {
      T instance = null;
      try {
         Class environmentClazz = environment.get(interfaceClazz);
         if (environmentClazz == null) {
            throw new RuntimeException(
             String.format("not environment for interface : %s", interfaceClazz.getName()));
         }
         instance = (T) environmentClazz.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
         e.printStackTrace();
      }
      return instance;
   }

   protected Environment() {
      LOG.debug("environment initiation");
      Environment.instance = this;
   }

   protected void changeEnvironment(Map<Class, Class> environment) {
      LOG.info("environment changed");
      this.environment.putAll(environment);
      Environment.instance = this;
   }
}
