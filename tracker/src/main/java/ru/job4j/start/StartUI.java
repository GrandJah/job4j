package ru.job4j.start;

import ru.job4j.action.TrackerAction;
import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 25.05.2017
 */
public class StartUI {
    /**
     * Ввод-вывод.
     */
    private Input input;

    /**
     * Трэкер заявок.
     */
    private Tracker tracker;

    /**
     * Меню.
     */
    private Menu menu;

    /**
     * @param tracker трэкер
     * @param input интерфейс пользователя
     * @param menu меню взаимодействия
     */
    private StartUI(Tracker tracker, ConsoleInput input, Menu menu) {
        this.input = input;
        this.tracker = tracker;
        this.menu = menu;
    }

    /**
     * @param args аргументы
     */
    public static void main(String[] args) {
        StartUI startUI = new StartUI(new Tracker(), new ConsoleInput(), new Menu());
        startUI.run();
    }

    /**
     * Application body.
     */
    private void run() {
        boolean exit = false;
        while (!exit) {
            this.input.println(this.menu.printMenu());
            try {
                int answer = Integer.parseInt(this.input.ask("Select : "));
                TrackerAction action = this.menu.getAction(answer);
                if (action != null) {
                    action.execute(this.input, this.tracker);
                } else {
                    exit = true;
                }
            } catch (NumberFormatException e) {
                this.input.println("Ошибка ввода: введите число");
            }
        }
    }
}
