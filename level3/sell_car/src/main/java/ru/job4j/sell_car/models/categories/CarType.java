package ru.job4j.sell_car.models.categories;

public class CarType implements Category {

   @Override
   public String text() {
      return "Car type";
   }

   @Override
   public String propertyName() {
      return "carType";
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
      SEDAN("Sedan"),
      SUV("SUV"),
      PICKUP("Pickup"),
      COUPE("Coupe"),
      MINIVAN("Minivan"),
      WAGON("Wagon"),
      HATCHBACK("Hatchback"),
      CONVERTIBLE("Convertible"),
      VAN("Van");

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
