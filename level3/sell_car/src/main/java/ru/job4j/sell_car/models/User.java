package ru.job4j.sell_car.models;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @JoinColumn(name = "name", unique = true, nullable = false)
   private String name;

   @JoinColumn(name = "email", unique = true)
   private String email;

   @JoinColumn(name = "phone", unique = true)
   private String phone;

   private Timestamp registration = Timestamp.from(Instant.now());

   public static User of(String name) {
      User user = new User();
      user.setName(name);
      return user;
   }
}
