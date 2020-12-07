package ru.job4j.todolist.storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class StorageInstance {
   private static final Map<Class, Storage> storageInstance = new HashMap<>();
   private static final Properties properties = new Properties();
   private static final ClassLoader loader = StorageInstance.class.getClassLoader();
   
   static {
      try {
         properties.load(loader.getResourceAsStream("application.properties"));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static <T> Storage<T> instance(Class<T> clazz) {
      storageInstance.computeIfAbsent(clazz, StorageInstance::newInstance);
      return storageInstance.get(clazz);
   }

   private static <T> Storage<T> newInstance(Class<T> clazz) {
      System.out.println(clazz.getName());
      try {
         return (Storage<T>) loader.loadClass(properties.getProperty(clazz.getName())).newInstance();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }
}
