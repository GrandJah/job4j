package ru.job4j.start;

import ru.job4j.tracker.HbmTracker;
import ru.job4j.tracker.adapters.StoreToTrackerAdapter;

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
        StartUI startUI = new StartUI(new StoreToTrackerAdapter(new HbmTracker()), new ValidateInput());
        startUI.run();
    }
}
