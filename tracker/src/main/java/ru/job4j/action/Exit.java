package ru.job4j.action;

import ru.job4j.start.Input;
import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 27.05.2017
 */
public class Exit implements TrackerAction {
    @Override
    public void execute(Input input, Tracker tracker) {
        input.println("Программа завершена");
    }
}
