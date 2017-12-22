package ru.job4j.tracker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.05.2017
 */
public class Item {
    /**
     * Id заявки.
     */
    private String id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Описание задачию.
     */
    private String description = "";

    /**
     * Время создания в миллисекундах.
     */
    private long created;

    /**
     * Комментарии к заявки.
     */
    private ArrayList<String> comments = new ArrayList<>();

    /**
     * Инициализация id и created.
     */
    private void init() {
        this.created = System.currentTimeMillis();
        this.id = String.format("%tD-%08d-%04d",
                this.created, (long) (Math.random() * 100000000), this.hashCode() % 1000);
    }

    /**
     * Default constructor.
     */
    public Item() {
        init();
        this.name = "unknown";
    }

    /**
     * @param name Имя пользователя
     */
    public Item(String name) {
        init();
        this.name = name;
    }

    /**
     * @param name        Имя пользователя
     * @param description Описание заявки
     */
    public Item(String name, String description) {
        init();
        this.name = name;
        this.description = description;
    }

    /** Полный конструктор.
     * @param id  Id заявки.
     * @param name    Имя пользователя
     * @param description Описание заявки
     * @param created    Время создания
     * @param comments Комментарии к заявке
     */
    private Item(String id, String name, String description, long created, ArrayList<String> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.comments = comments;
    }

    /**
     * @return Идентификатор
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return Название заявки
     */
    public String getName() {
        return this.name;
    }

    /**
     * Редактирование имени.
     *
     * @param name новое имя.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter.
     *
     * @return description description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * * Редактирование описания.
     *
     * @param description описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter.
     *
     * @return created created
     */
    public long getCreated() {
        return this.created;
    }

    /**
     * Getter.
     *
     * @return comments comments
     */
    public ArrayList<String> getComments() {
        return this.comments;
    }

    @Override
    public String toString() {
        return String.format("Item{%6$s id = '%s',%6$s name = '%s',%6$s description = '%s',%6$s created = '%4$te %4$tb,%4$tY - %4$tH:%4$tM:%4$tS',%6$s comments = '%5$s',%6$s}%6$s",
                id, name, description, created, comments, System.lineSeparator());
    }

    @Override
    public boolean equals(Object o) {
        boolean success = this == o || o != null && getClass() == o.getClass();
        if (success) {
            Item item = (Item) o;
            success = created / 1000 == item.created / 1000 // Проблема с переворотом из Timestamp
                    && Objects.equals(id, item.id)
                    && Objects.equals(name, item.name)
                    && Objects.equals(description, item.description);
        }
        return success;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created);
    }

    /** Сборка Item из запросов БД.
     * @param item Строка item
     * @param comments Строка comments
     * @return Item
     * @throws SQLException ошибка БД
     */
    public static Item convertFromDB(ResultSet item, ResultSet comments) throws SQLException {
        ArrayList<String> commentArray = new ArrayList<>();
        while (comments.next()) {
            commentArray.add(comments.getString("comment"));
        }
        return new Item(item.getString("id"),
                item.getString("name"),
                item.getString("description"),
                item.getTimestamp("created").getTime(),
                commentArray);
    }
}
