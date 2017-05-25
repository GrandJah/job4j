package ru.job4j.action;

import ru.job4j.tracker.Item;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 25.05.2017
 */
public class GetAll extends TrackerAction {
    @Override
    public void execute() {
        Item[] items = getTracker().getAll();
        for (Item item : items) {
            getInput().println(item.getId());
        }
    }
}
