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

    /** Редактирование названия.
     * @param name новое название
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Название заявки.
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
        this.id = String.format("%tD-%03d",
                this.created, this.created % 1000);
    }

    /**
     * Default constructor.
     */
    public Item() {
        init();
    }

    /**
     * @param name Название заявки
     */
    public Item(String name) {
        init();
        this.name = name;
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
}
