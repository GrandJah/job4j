package models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "drivers")
@EqualsAndHashCode(exclude = "drivers")
@Table(name = "cars")
public class Car {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String number;

   @ManyToOne
   @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "ENGINE_ID_FK"))
   private Engine engine;


   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinTable(name = "history_owner", joinColumns = {
    @JoinColumn(name = "car_id", nullable = false, updatable = false)
   }, inverseJoinColumns = {
    @JoinColumn(name = "driver_id", nullable = false, updatable = false)
   })
   public Set<Driver> drivers = new HashSet<>();

   public void addDriver(Driver driver) {
      this.drivers.add(driver);
   }

   public static Car of(String carNamber) {
      Car car = new Car();
      car.setNumber(carNamber);
      return car;
   }
}
