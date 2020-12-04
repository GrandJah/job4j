package ru.job4j.tracker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * job4j.
 *
 * @author Igor Kovalkov
 * @version 0.2
 * @since 04.12.2020
 */
@Data
@NoArgsConstructor(force = true)
@Entity
@Table(name = "items")
public class Item {
  /**
   * Id заявки.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * Имя пользователя.
   */
  private String name;

  /**
   * @param name Имя пользователя
   */
  public Item(String name) {
    this.name = name;
  }
}

