package ru.job4j.start;

import ru.job4j.tracker.TrackerArray;
/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.12.2017
 */
public class MainArray {
    /**
     * @param args аргументы
     */
    public static void main(String[] args) {
        StartUI startUI = StartUI.of(new TrackerArray(), new ValidateInput());
        startUI.run();
    }
}
