package ru.job4j.profession;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 23.05.2017
 */
public abstract class Profession {
    /** Имя. */
    private String name;

    /**
     * @param name Название профессии
     */
    public Profession(String name) {
        this.name = name;
    }

    /**
     * @return Имя
     */
    public String getName() {
        return this.name;
    }
}
