package ru.job4j.action;

import ru.job4j.start.Input;
import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 25.05.2017
 */
public abstract class TrackerAction {
    /**
     * Объект пользовательского интерфейса.
     */
    private Input input;

    /**
     * Трэкер над которым выдолняются действия.
     */
    private Tracker tracker;

    /** Выполнить действие.*/
    public abstract void execute();

    /**
     * @param input Input
     */
    public void setInput(Input input) {
        this.input = input;
    }

    /**
     * @param tracker Tracker
     */
    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    /**
     * @return Input
     */
    protected Input getInput() {
        return this.input;
    }

    /**
     * @return Tracker
     */
    protected Tracker getTracker() {
        return this.tracker;
    }
}
