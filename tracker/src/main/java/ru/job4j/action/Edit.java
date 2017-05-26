package ru.job4j.action;

import ru.job4j.start.Input;
import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 26.05.2017
 */
public class Edit implements TrackerAction {
    @Override
    public void execute(Input input, Tracker tracker) {
        Item item = tracker.findByName(input.ask("Введите идентификатор:"));
        String answer = input.ask("Введите новое имя:");
        if (!answer.equals("")) {
            item.setName(answer);
        }
        answer = input.ask("Введите новое описание:");
        if (!answer.equals("")) {
            item.setDescription(answer);
        }
        tracker.update(item);
    }
}
