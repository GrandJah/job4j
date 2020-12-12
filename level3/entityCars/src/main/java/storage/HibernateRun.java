package storage;

import java.util.Optional;
import models.Car;
import models.Driver;

public class HibernateRun extends HbmStorage {

   public static void main(String[] args) {
      new HibernateRun().run();
   }

   public void run() {
      createEntry("Ivanov", "Camry", "Land Cruiser", "Corolla", "Fortuner", "Hiace");
      createEntry("Petrov", "Solaris", "Creta", "Accent");
      createEntry("Sidorov", "Solaris", "Corolla", "Camry");
   }

   public Car createEntry(String carNamber, String... drivers) {
      return query(sf -> {
         Car car = Optional.ofNullable(
          sf.createQuery("from Car where number = :number", Car.class)
           .setParameter("number", carNamber).uniqueResult())
                                             .orElse(Car.of(carNamber));
         for (String driverName : drivers) {
            car.addDriver(Optional.ofNullable(
             sf.createQuery("from Driver where name = :name", Driver.class)
              .setParameter("name", driverName).uniqueResult())
                                  .orElse(Driver.of(driverName)));
         }
         sf.save(car);
         return car;
      });
   }
}
