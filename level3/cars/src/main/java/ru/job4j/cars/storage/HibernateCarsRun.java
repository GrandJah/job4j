package ru.job4j.cars.storage;

import ru.job4j.cars.models.Manufacturer;
import ru.job4j.cars.models.Model;

public class HibernateCarsRun extends HbmStorage {

   public static void main(String[] args) {
      HibernateCarsRun app = new HibernateCarsRun();
      app.createEntry("Toyota",
       "Camry", "Land Cruiser", "Corolla", "Fortuner", "Hiace");
      app.createEntry("Hyundai",
       "Solaris", "Creta", "Accent");
   }

   public void createEntry(String manufacturer, String... models) {
      Manufacturer brand = Manufacturer.ofName(manufacturer);
      for (String model : models) {
         brand.addModel(Model.ofName(model));
      }
      save(brand);
   }
}
