package ru.job4j.tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
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
    private String[] comments;

    /**
     * Инициализация id и created.
     */
    private void init() {
        this.created = System.currentTimeMillis();
        this.id = String.format("%tD-%080d",
                this.created, this.created % 100000000);
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
     * @param name Имя пользователя
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

    /** Редактирование имени.
     * @param name новое имя.
     */
    public void setName(String name) {
        this.name = name;
    }
}
