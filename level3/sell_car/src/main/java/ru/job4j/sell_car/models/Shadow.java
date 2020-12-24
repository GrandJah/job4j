package ru.job4j.sell_car.models;

import java.util.Objects;
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

   @JoinColumn(name = "token")
   private String token;

   public static Shadow of(User user, String pass) {
      Shadow shadow = new Shadow();
      shadow.setUser(user);
      shadow.setSecretHash(shadow.createSecretHash(pass));
      return shadow;
   }

   public static String createToken(User user) {
      return String.valueOf(Math.random()); //todo функция для создания токена
   }

   private String createSecretHash(String pass) {
      return pass; //todo функция для создания хэша пароля
   }

   public boolean checkPass(String checkPass) {
      return Objects.equals(this.secretHash, createSecretHash(checkPass));
   }
}
