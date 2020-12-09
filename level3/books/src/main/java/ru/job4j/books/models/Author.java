package ru.job4j.books.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "author")
public class Author {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(unique = true)
   private String name;

   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   private List<Book> books = new ArrayList<>();

   public Author addBook(Book books) {
      this.books.add(books);
      return this;
   }

   public static Author ofName(String name) {
      Author entity = new Author();
      entity.setName(name);
      return entity;
   }
}
