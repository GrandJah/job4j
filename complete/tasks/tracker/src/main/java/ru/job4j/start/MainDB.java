package ru.job4j.start;

import ru.job4j.tracker.TrackerDB;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.12.2017
 */
public class MainDB {
    /**
     * @param args аргументы
     */
    public static void main(String[] args) {
        StartUI startUI = new StartUI(new TrackerDB(), new ValidateInput());
        startUI.run();
    }
}
