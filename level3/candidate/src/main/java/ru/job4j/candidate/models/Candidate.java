package ru.job4j.candidate.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "candidate")
public class Candidate {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String name;

   private Integer experience;

   private Integer salary;

   public static Candidate of(String name, Integer experience, Integer salary) {
      Candidate candidate = new Candidate();
      candidate.name = name;
      candidate.experience = experience;
      candidate.salary = salary;
      return candidate;
   }
}
