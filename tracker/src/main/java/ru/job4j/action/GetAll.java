package ru.job4j.action;

import ru.job4j.start.Input;
import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 25.05.2017
 */
public class GetAll implements TrackerAction {
    @Override
    public void execute(Input input, Tracker tracker) {
        Item[] items = tracker.getAll();
        for (Item item : items) {
            input.println(item.toString());
        }
    }
}
