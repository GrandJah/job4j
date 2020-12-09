package ru.job4j.cars.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(exclude = "models")
@ToString(exclude = "models")
@Table(name = "manufacturers")
public class Manufacturer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(unique = true)
   private String name;

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "manufacturer")
   private Set<Model> models = new HashSet<>();

   public Manufacturer addModel(Model model) {
      models.add(model);
      model.setManufacturer(this);
      return this;
   }

   public static Manufacturer ofName(String name) {
      Manufacturer entity = new Manufacturer();
      entity.setName(name);
      return entity;
   }
}
