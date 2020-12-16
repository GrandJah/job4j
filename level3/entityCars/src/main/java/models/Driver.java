package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "cars")
@EqualsAndHashCode(exclude = "cars")
@Table(name = "drivers")
public class Driver {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String name;

   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinTable(name = "history_owner", joinColumns = {
    @JoinColumn(name = "driver_id", nullable = false)
   }, inverseJoinColumns = {
    @JoinColumn(name = "car_id", nullable = false)
   })
   private List<Car> cars = new ArrayList<>();

   public void addCar(Car car) {
      this.cars.add(car);
   }

   public static Driver of(String driverName) {
      Driver driver = new Driver();
      driver.setName(driverName);
      return driver;
   }
}
