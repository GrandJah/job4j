package ru.job4j.start;

import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 25.05.2017
 */
public class StartUI {

    /**
     * @param args аргументы
     */
    public static void main(String[] args) {
        StartUI startUI = new StartUI();
        startUI.run();
    }

    /**
     * Application body.
     */
    private void run() {
        MenuTracker menu = new MenuTracker(new Tracker(), new ConsoleInput());
        while (true) {
            menu.pass();
        }
    }
}
