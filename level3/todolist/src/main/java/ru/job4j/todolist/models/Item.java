package ru.job4j.todolist.models;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 * Model Item.
 */
@Data
@Entity
@Table(name = "items")
public class Item {
   /**
    * id.
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   /**
    * task name.
    */
   private String task;

   /**
    * description task.
    */
   private String description;

   /**
    * time created.
    */
   @Temporal(TemporalType.TIMESTAMP)
   private Date created = Date.from(Instant.now());

   /**
    * item done.
    */
   private Boolean done = false;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User author;

   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
   private Set<Category> categories = new HashSet<>();

   public void addCategory(Category category) {
      this.categories.add(category);
   }
}
