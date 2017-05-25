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
    private Input input;

    private Tracker tracker;

    /** Выполнить действие.*/
    public abstract void execute();

    public void setInput(Input input) {
        this.input = input;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    protected Input getInput() {
        return this.input;
    }

    protected Tracker getTracker() {
        return this.tracker;
    }
}
