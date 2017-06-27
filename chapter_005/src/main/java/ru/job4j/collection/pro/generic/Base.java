package ru.job4j.collection.pro.generic;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.06.2017
 */
public abstract class Base {
    /**
     * Идентификатор.
     */
    private String id;

    /**
     * @return Идентификатор
     */
    String getId() {
        return id;
    }

    /**
     * @param id Идентификатор
     */
    void setId(String id) {
        this.id = id;
    }
}
