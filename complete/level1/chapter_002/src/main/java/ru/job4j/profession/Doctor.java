package ru.job4j.profession;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 23.05.2017
 */
public class Doctor extends Profession {
    /**
     * Уровень квалификации.
     */
    private int qualification;

    /**
     * @param name Имя
     * @param qualification уровень квалификации
     */
    public Doctor(String name, int qualification) {
        super(name);
        this.qualification = qualification;
    }

    /** Доктор лечит.
     * @param human человек
     * @return действие
     */
    public String heal(Human human) {
        return String.format("Доктор %s лечит %s", this.getName(), human.getName());
    }


}
