package ru.job4j.action;

import ru.job4j.tracker.Item;

/** Добавления заявки.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 25.05.2017
 */
public class Add extends TrackerAction {
    public void execute() {
        String name = getInput().ask("Введите ваше имя: ");
        String description = getInput().ask("Введите описание заявки: ");
        getTracker().add(new Item(name, description));
    }
}
