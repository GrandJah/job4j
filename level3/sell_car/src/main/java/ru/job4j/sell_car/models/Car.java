package ru.job4j.sell_car.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import ru.job4j.sell_car.models.categories.CarType;
import ru.job4j.sell_car.models.categories.FuelType;
import ru.job4j.sell_car.models.categories.GearType;
import ru.job4j.sell_car.models.categories.WheelDriveType;

@Data
@Entity
@Table(name = "cars")
public class Car {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String model;

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<ImageFile> images = new HashSet<>();

   @Enumerated(EnumType.STRING)
   private GearType gearbox;

   @Enumerated(EnumType.STRING)
   private FuelType fuelType;

   @Enumerated(EnumType.STRING)
   private WheelDriveType drive;

   @Enumerated(EnumType.STRING)
   private CarType carType;
}
