package ru.job4j.sell_car.models.categories;

public class WheelDriveType implements Category {

   @Override
   public String text() {
      return "Wheel drive type";
   }

   @Override
   public String propertyName() {
      return "wd_type";
   }

   @Override
   public CategoryValue[] getSetValues() {
      return Value.values();
   }

   @Override
   public CategoryValue getValue(String valueName) {
      return Value.valueOf(valueName);
   }

   public enum Value implements CategoryValue {
      FrontWD("Front WD"), RearWD("Rear WD"), FullWD("Full WD");

      Value(String text) {
         this.text = text;
      }

      private final String text;

      @Override
      public String text() {
         return this.text;
      }
   }
}
