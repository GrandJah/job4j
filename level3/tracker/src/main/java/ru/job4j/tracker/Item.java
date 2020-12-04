package ru.job4j.tracker;

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
public class Item {
  /**
   * Id заявки.
   */
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

