package ru.job4j.candidate.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vacancy")
public class Vacancy {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String name;

   public static Vacancy of(String name) {
      Vacancy vacancy = new Vacancy();
      vacancy.name = name;
      return vacancy;
   }
}
