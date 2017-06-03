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
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    void execute(Input input, Tracker tracker) throws Tracker.ErrorValue, Tracker.NotFound;

    /**
     * @return Строка описания действия
     */
    String getInfo();
}
