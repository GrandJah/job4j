package ru.job4j.start;

import ru.job4j.action.Add;
import ru.job4j.action.Exit;
import ru.job4j.action.GetAll;
import ru.job4j.action.TrackerAction;
import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 25.05.2017
 */
class MenuTracker {
    /**
     * Ввод-вывод.
     */
    private Input input;

    /**
     * Трэкер заявок.
     */
    private Tracker tracker;

    /**
     * User menu.
     */
    private MenuItem[] menu;

    /**
     * @param tracker трэкер заявок
     * @param input интерфейс взаимодействия
     */
    MenuTracker(Tracker tracker, Input input) {
        this.tracker = tracker;
        this.input = input;
        this.menu = new MenuItem[]{
                new MenuItem("Add new Item", new Add()),
                new MenuItem("Show all items", new GetAll()),
                new MenuItem("Edit item"),
                new MenuItem("Delete item"),
                new MenuItem("Find item by Id"),
                new MenuItem("Find items by name"),
                new MenuItem("Exit Program", new Exit())
        };
    }

    /**
     * Проход взаимодействия с пользователем.
     */
    void pass() {
        printMenu();
        try {
            int answer = Integer.parseInt(input.ask("Select : "));
            action(answer);
        } catch (NumberFormatException e) {
            this.input.println("Ошибка ввода: введите число");
        }

    }

    /**
     * Вывод полного меню.
     */
    private void printMenu() {
        StringBuilder out = new StringBuilder();
        for (int index = 0; index < this.menu.length; index++) {
            out.append(index);
            out.append(". ");
            out.append(this.menu[index].getString());
            out.append(System.lineSeparator());
        }
        this.input.print(out.toString());
    }

    /** Выполенение пункта меню.
     * @param index пункт меню
     */
    private void action(int index) {
        if (index < this.menu.length && index >= 0) {
            TrackerAction action = this.menu[index].getAction();
            action.setInput(this.input);
            action.setTracker(this.tracker);
            action.execute();
        } else {
            input.println("Ошибка: данного пункта нет в меню");
        }
    }

    /**
     * Элемент пункта меню.
     */
    private static class MenuItem {
        /**
         * Строка меню.
         */
        private String string;
        /**
         * Выполняемое действие.
         */
        private TrackerAction action;

        /** Пункт с действием по умолчанию.
         * @param string Строка меню
         */
        MenuItem(String string) {
            this.string = string;
            this.action = new EmptyAction();
        }

        /**
         * @param string Строка меню
         * @param action Выполняемое действие
         */
        MenuItem(String string, TrackerAction action) {
            this.string = string;
            this.action = action;
        }

        /**
         * @return Строка меню
         */
        String getString() {
            return this.string;
        }

        /**
         * @return TrackerAction пункта меню
         */
        TrackerAction getAction() {
            return this.action;
        }

        /**
         * Действие по умолчанию, для пункта меню.
         */
        private class EmptyAction extends TrackerAction {
            @Override
            public void execute() {
                getInput().println("Действие на этот пункт не реализвано!");
            }
        }
    }
}




