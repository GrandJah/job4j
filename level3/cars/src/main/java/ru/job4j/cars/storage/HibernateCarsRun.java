package ru.job4j.cars.storage;

import java.util.List;
import java.util.Optional;
import ru.job4j.cars.models.Manufacturer;
import ru.job4j.cars.models.Model;

public class HibernateCarsRun extends HbmStorage {

   public static void main(String[] args) {
      new HibernateCarsRun().run();
   }

   public void run() {
      createEntry("Toyota", "Camry", "Land Cruiser", "Corolla", "Fortuner", "Hiace");
      createEntry("Hyundai", "Solaris", "Creta", "Accent");
      loadEntity();
      loadEntityFetch();
   }

   public Manufacturer createEntry(String authorName, String... models) {
      return query(sf -> {
         Manufacturer manufacturer = Optional.ofNullable(
          sf.createQuery("from Manufacturer where name = :name", Manufacturer.class)
           .setParameter("name", authorName).uniqueResult())
          .orElse(Manufacturer.ofName(authorName));
         for (String modelName : models) {
            manufacturer.addModel(Optional.ofNullable(
             sf.createQuery("from Model where name = :name", Model.class)
              .setParameter("name", modelName).uniqueResult()).orElse(Model.ofName(modelName)));
         }
         sf.save(manufacturer);
         return manufacturer;
      });
   }

   public void loadEntity() {
      query(sf -> {
         List<Manufacturer> list = sf.createQuery("from Manufacturer", Manufacturer.class).list();
         for (Manufacturer manufacturer : list) {
            for (Model model : manufacturer.getModels()) {
               System.out.println(model);
            }
         }
         return null;
      });
   }

   public void loadEntityFetch() {
      List<Manufacturer> list = query(sf -> sf
       .createQuery("select distinct m from Manufacturer m join fetch m.models", Manufacturer.class)
       .list());
      for (Manufacturer manufacturer : list) {
         for (Model model : manufacturer.getModels()) {
            System.out.println(model);
         }
      }
   }
}
