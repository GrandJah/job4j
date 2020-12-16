package storage;

import java.util.List;
import java.util.Optional;
import models.Car;
import models.Driver;
import models.Engine;
import models.HistoryOwner;

public class HibernateRun extends HbmStorage {

   public static void main(String[] args) {
      new HibernateRun().run();
   }

   public void run() {
      listHistoryOwner();
      createEngine("low", "medium", "high");
      createCar("Camry", "low");
      createCar("Solaris", "medium");
      createCar("Corolla", "new model");
      createEntry("Ivanov", "Camry", "Land Cruiser", "Corolla", "Fortuner", "Hiace");
      createEntry("Petrov", "Solaris", "Creta", "Accent");
      createEntry("Sidorov", "Solaris", "Corolla", "Camry");
   }

   private void listHistoryOwner() {
      List<HistoryOwner> list = query(sf -> sf.createQuery("from HistoryOwner", HistoryOwner.class).list());
      for (HistoryOwner hO : list) {
         System.out.println(hO.toString());
      }
   }

   private void createCar(String carNumber, String engineModel) {
      query(sf -> {
         Engine engine = Optional.ofNullable(
          sf.createQuery("from Engine where model = :model", Engine.class)
            .setParameter("model", engineModel).uniqueResult()).orElse(Engine.of(engineModel));
         Car car = Optional.ofNullable(sf.createQuery("from Car where serialNumber = :number", Car.class)
                                         .setParameter("number", carNumber).uniqueResult())
                           .orElse(Car.of(carNumber));
         car.setEngine(engine);
         sf.save(car);
         return null;
      });
   }

   private void createEngine(String... engineModels) {
      for (String model : engineModels) {
         query(sf -> {
            Engine engine = Optional.ofNullable(
             sf.createQuery("from Engine where model = :model", Engine.class)
               .setParameter("model", model).uniqueResult()).orElse(Engine.of(model));
            sf.save(engine);
            return null;
         });
      }

   }

   public Driver createEntry(String driverName, String... carNambers) {
      return query(sf -> {
         Driver driver = Optional.ofNullable(
          sf.createQuery("from Driver where name = :name", Driver.class)
            .setParameter("name", driverName).uniqueResult()).orElse(Driver.of(driverName));
         for (String carNamber : carNambers) {
            driver.addCar(Optional.ofNullable(
             sf.createQuery("from Car where serialNumber = :number", Car.class)
               .setParameter("number", carNamber).uniqueResult()).orElse(Car.of(carNamber)));
         }
         sf.save(driver);
         return driver;
      });
   }
}
