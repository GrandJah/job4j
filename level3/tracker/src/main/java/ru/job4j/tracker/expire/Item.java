package ru.job4j.tracker.expire;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * junior.
 * todo replace implementations on the "ru.job4j.tracker.Item"
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.05.2017
 */
@Data
public class Item extends ru.job4j.tracker.Item {
  private static int GEN_ID = (int) System.currentTimeMillis();

  /**
   * Описание задачию.
   */
  private String description = "";

  /**
   * Время создания в миллисекундах.
   */
  private long created = (System.currentTimeMillis()/1000)*1000;
  //Problem with converting the database type "timestamp"

  /**
   * Комментарии к заявки.
   */
  private List<String> comments = new ArrayList<>();

  /**
   * Default constructor.
   */
  public Item() {
    this("unknown");
  }

  /**
   * @param name Имя пользователя
   */
  public Item(String name) {
    super(name);
    setId(GEN_ID++);
  }

  /**
   * @param name        Имя пользователя
   * @param description Описание заявки
   */
  public Item(String name, String description) {
    this(name);
    this.description = description;
  }

  /**
   * Полный конструктор.
   *
   * @param id          Id заявки.
   * @param name        Имя пользователя
   * @param description Описание заявки
   * @param created     Время создания
   * @param comments    Комментарии к заявке
   */
  private Item(Integer id, String name, String description, long created, List<String> comments) {
    this(name);
    setId(id);
    this.description = description;
    this.created = created;
    this.comments = comments;
  }

  /**
   * Сборка Item из запросов БД.
   *
   * @param item     Строка item
   * @param comments Строка comments
   * @return Item
   * @throws SQLException ошибка БД
   */
  public static Item convertFromDB(ResultSet item, ResultSet comments) throws SQLException {
    List<String> commentArray = new ArrayList<>();
    while (comments.next()) {
      commentArray.add(comments.getString("comment"));
    }

    return new Item(item.getInt("id"),
        item.getString("name"),
        item.getString("description"),
        item.getTimestamp("created").getTime(),
        commentArray);
  }

  @Override
  public String toString() {
    return "Item{" +
        "id=" + getId() +
        ", name='" + getName() + '\'' +
        ", description='" + description + '\'' +
        ", created=" + created +
        ", comments=" + comments +
        '}';
  }
}
