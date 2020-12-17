package ru.job4j.sell_car.models;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "adverts")
public class Advert {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @JoinColumn(name = "price", nullable = false)
   private String description = "";

   @JoinColumn(name = "price", nullable = false)
   private Integer price = 0;

   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "user_id")
   private User user;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "car_id")
   private Car car;

   @JoinColumn(name = "status", nullable = false)
   private Boolean status = false;

   private Timestamp created = Timestamp.from(Instant.now());
}
