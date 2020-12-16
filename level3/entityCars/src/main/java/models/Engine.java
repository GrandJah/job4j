package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "engines")
public class Engine {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String model;

   private String serialNumber;

   public static Engine of(String model) {
      Engine engine = new Engine();
      engine.setModel(model);
      return engine;
   }
}
