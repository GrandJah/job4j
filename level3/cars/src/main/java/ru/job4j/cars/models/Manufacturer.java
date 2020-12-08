package ru.job4j.cars.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "manufacturers")
public class Manufacturer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(unique = true)
   private String name;

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Model> models = new ArrayList<>();

   public Manufacturer addModel(Model model) {
      models.add(model);
      return this;
   }

   public static Manufacturer ofName(String name) {
      Manufacturer entity = new Manufacturer();
      entity.setName(name);
      return entity;
   }
}
