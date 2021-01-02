package ru.job4j.sell_car.models.categories;

public class GearType implements Category {

   @Override
   public String text() {
      return "Gear Type";
   }

   @Override
   public String propertyName() {
      return "gear_type";
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
      AUTO("Automatic"), MANUAL("Manual");

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
