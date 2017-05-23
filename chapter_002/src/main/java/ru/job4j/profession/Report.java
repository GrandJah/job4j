package ru.job4j.profession;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 23.05.2017
 */
public class Report implements Document {
    /**
     * Отчет название и содержание.
     */
    private String name, content;

    /**
     * @param name Название
     * @param content Содержание
     */
    public Report(String name, String content) {
        this.name = name;
        this.content = content;
    }

    /**
     * @return название документа
     */
    @Override
    public String getNameDocument() {
        return this.name;
    }

    /**
     * @return отчет
     */
    public String getReport() {
        return content;
    }
}
