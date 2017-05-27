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
public class Delete implements TrackerAction {
    @Override
    public void execute(Input input, Tracker tracker) {
        tracker.delete(tracker.findById(input.ask("Введите идентификатор:")));
    }
}
