package ru.job4j.bomberman.helper;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.12.2017
 */
public abstract class Entity {
    /**
     * Типы объектов.
     */
    protected enum Type {
        /**
         * Бомбермэн.
         */
        BomberMan,
        /**
         * Чудовище.
         */
        Monster,
        /**
         * Стены.
         */
        Wall
    }

    /**
     * тип Объекта.
     */
    private final Type type;

    /**
     * @param type тип Объекта
     */
    protected Entity(Type type) {
        this.type = type;
    }

    /** Сигналы между объектами.
     * @param entity Объект подающий сигнал
     */
    public void signal(Entity entity) {
    }

    /**
     * @return type type
     */
    public Type type() {
        return this.type;
    }
}
