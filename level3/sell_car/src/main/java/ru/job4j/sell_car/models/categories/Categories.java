package ru.job4j.sell_car.models.categories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Categories {
   private static final Map<String, Category> CATEGORY_MAP = new HashMap<>();

   static {
      addCategory(CarType.class);
      addCategory(FuelType.class);
      addCategory(GearType.class);
      addCategory(WheelDriveType.class);
   }

   private static void addCategory(Class<? extends Category> categoryClass) {
      try {
         Category category = categoryClass.newInstance();
         CATEGORY_MAP.put(category.propertyName(), category);
      } catch (InstantiationException | IllegalAccessException e) {
         e.printStackTrace();
      }
   }

   public static Category getCategory(String propName) {
      return CATEGORY_MAP.get(propName);
   }

   public static Collection<Category> categories() {
      return CATEGORY_MAP.values();
   }

   public static String getNameProperty(Class<Category> categoryClass) {
      try {
         return categoryClass.newInstance().propertyName();
      } catch (InstantiationException | IllegalAccessException e) {
         e.printStackTrace();
         return "";
      }
   }
}
