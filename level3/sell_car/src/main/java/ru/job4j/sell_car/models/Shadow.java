package ru.job4j.sell_car.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "shadows")
public class Shadow {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "user_id", unique = true, nullable = false)
   private User user;

   @JoinColumn(name = "secret_hash", nullable = false)
   private String secretHash;
}
