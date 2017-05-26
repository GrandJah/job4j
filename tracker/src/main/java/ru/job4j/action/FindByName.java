package ru.job4j.action;

import ru.job4j.start.Input;
import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 26.05.2017
 */
public class FindByName implements TrackerAction {
    @Override
    public void execute(Input input, Tracker tracker) {
        input.println(tracker.findByName(input.ask("Введите имя:")).toString());
    }
}
