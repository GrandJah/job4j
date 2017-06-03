package ru.job4j.tracker;

import java.util.ArrayList;

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
    private String description;

    /**
     * Время создания в миллисекундах.
     */
    private long created;

    /**
     * Комментарии к заявки.
     */
    private ArrayList<String> comments;

    /**
     * Инициализация id и created.
     */
    private void init() {
        this.created = System.currentTimeMillis();
        this.id = String.format("%tD-%08d-%04d",
                this.created, this.created % 100000000, this.hashCode() % 1000);
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
     * * Редактирование описания.
     *
     * @param description описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Item{%6$s id = '%s',%6$s name = '%s',%6$s description = '%s',%6$s created = '%4$te %4$tb,%4$tY - %4$tH:%4$tM:%4$tS',%6$s comments = '%5$s',%6$s}%6$s",
                id, name, description, created, comments, System.lineSeparator());
    }
}
