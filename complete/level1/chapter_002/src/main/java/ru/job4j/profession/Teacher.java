package ru.job4j.profession;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 23.05.2017
 */
public class Teacher extends Profession {
    /**
     * Номер школы.
     */
    private int school;

    /**
     * @param name Имя
     * @param school Номер школы
     */
    public Teacher(String name, int school) {
        super(name);
        this.school = school;
    }

    /** Учит студентов.
     * @param student ученик
     * @return действие
     */
    public String teach(Student student) {
        return String.format("Учитель %s учит ученика %s", this.getName(), student.getName());
    }
}
