package ru.job4j.convert;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class User {
    /**
     * Идентификатор.
     */
    private int id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Город.
     */
    private String city;

    /**
     * Getter.
     * @return Id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param name Имя
     * @param city Город
     */
    public User(String name, String city) {
        this.name = name;
        this.city = city;
        this.id = name.hashCode() * city.hashCode();
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && this.id == ((User) obj).id;
    }
}
