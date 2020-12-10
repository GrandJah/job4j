package ru.job4j.todolist.storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class StorageInstance {
   private static final Map<Class, Storage> STORAGE_INSTANCE = new HashMap<>();
   private static final Properties PROPERTIES = new Properties();
   private static final ClassLoader LOADER = StorageInstance.class.getClassLoader();

   static {
      try {
         PROPERTIES.load(LOADER.getResourceAsStream("application.properties"));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static <T> Storage<T> instance(Class<T> clazz) {
      STORAGE_INSTANCE.computeIfAbsent(clazz, StorageInstance::newInstance);
      return STORAGE_INSTANCE.get(clazz);
   }

   private static <T> Storage<T> newInstance(Class<T> clazz) {
      System.out.println(clazz.getName());
      try {
         return (Storage<T>) LOADER.loadClass(PROPERTIES.getProperty(clazz.getName()))
          .newInstance();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }
}
