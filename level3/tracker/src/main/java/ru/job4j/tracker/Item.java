package ru.job4j.tracker;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(exclude = "created")
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
   * Описание задачию.
   */
  private String description = "";

  /**
   * Время создания.
   */
  private Timestamp created = Timestamp.from(Instant.now().with(ChronoField.MILLI_OF_SECOND, 0));


  /**
   * Комментарии к заявки.
   */
  @Transient
  private List<String> comments = new ArrayList<>();

  /**
   * @param name Имя пользователя
   */
  public Item(String name) {
    this.name = name;
  }

  /**
   * @param name        Имя пользователя
   * @param description Описание заявки
   */
  public Item(String name, String description) {
    this(name);
    setDescription(description);
  }
}

