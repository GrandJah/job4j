package ru.job4j.candidate.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vacancy_base")
public class VacancyBase {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Vacancy> vacancies = new ArrayList<>();

   public VacancyBase addVacancy(Vacancy vacancy) {
      vacancies.add(vacancy);
      return this;
   }
}
