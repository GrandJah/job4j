package ru.job4j.todolist.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "users")
@ToString(exclude = {"password", "token"})
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(unique = true, nullable = false)
   private String name;

   @Column(nullable = false)
   private String password;

   @Column(unique = true)
   private String token;
}
