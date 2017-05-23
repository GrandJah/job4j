package ru.job4j.profession;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 23.05.2017
 */
public class Engineer extends Profession {
    /**
     * @param name Имя
     */
    public Engineer(String name) {
        super(name);
    }

    /** Производит расчеты.
     * @param task задача
     * @return действие
     */
    public String calculation(Project task) {
        return String.format("Инжинер %s производит расчеты %s", this.getName(), task.getNameDocument());
    }

    /** Составляет отчет.
     * @return Отчет
     */
    public Report report() {
        return new Report("Отчет", String.format("Отчет создан %s", this.getName()));
    }
}
