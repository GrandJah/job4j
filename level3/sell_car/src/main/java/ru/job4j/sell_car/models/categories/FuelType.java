package ru.job4j.sell_car.models.categories;

public class FuelType implements Category {

   @Override
   public String text() {
      return "Fuel type";
   }

   @Override
   public String propertyName() {
      return "fuelType";
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
      PETROL("Petrol"), DIESEL("Diesel"), LIQUEFIED_GAS("Liquefied gas");

      private final String text;

      Value(String text) {
         this.text = text;
      }

      @Override
      public String text() {
         return this.text;
      }
   }
}
