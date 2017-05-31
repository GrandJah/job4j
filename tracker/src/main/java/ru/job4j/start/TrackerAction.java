package ru.job4j.start;

import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 25.05.2017
 */
public interface TrackerAction {

    /** Выполнить действие.
     * @param input интерфейс пользователя
     * @param tracker трэкер
     */
    void execute(Input input, Tracker tracker);

    /**
     * @return Строка описания действия
     */
    String getInfo();
}