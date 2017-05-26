package ru.job4j.action;

import ru.job4j.start.Input;
import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

/** Добавления заявки.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 25.05.2017
 */
public class Add implements TrackerAction {
    @Override
    public void execute(Input input, Tracker tracker) {
        String name = input.ask("Введите ваше имя: ");
        String description = input.ask("Введите описание заявки: ");
        tracker.add(new Item(name, description));
    }
}
