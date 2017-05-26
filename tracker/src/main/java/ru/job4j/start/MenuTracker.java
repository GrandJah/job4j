package ru.job4j.start;

import ru.job4j.action.Add;
import ru.job4j.action.Delete;
import ru.job4j.action.Edit;
import ru.job4j.action.FindById;
import ru.job4j.action.FindByName;
import ru.job4j.action.GetAll;
import ru.job4j.action.TrackerAction;
import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 25.05.2017
 */
class MenuTracker {
    /**
     * User menu.
     */
    private MenuItem[] menu = new MenuItem[]{
                new MenuItem("Add new Item", new Add()),
                new MenuItem("Show all items", new GetAll()),
                new MenuItem("Edit item", new Edit()),
                new MenuItem("Delete item", new Delete()),
                new MenuItem("Find item by Id", new FindById()),
                new MenuItem("Find items by name", new FindByName()),
                new MenuItem("Exit Program", null)
        };

    /** Выполенение пункта меню.
     * @param index пункт меню
     * @return действие
     */
    public TrackerAction getAction(int index) {
        TrackerAction action;
        if (index < this.menu.length && index >= 0) {
            action = this.menu[index].getAction();
        } else {
            action = new ErrorAction();
        }
        return action;
    }

    /**
     * Вывод полного меню.
     * @return Меню
     */
    public String printMenu() {
        StringBuilder out = new StringBuilder();
        for (int index = 0; index < this.menu.length; index++) {
            out.append(index);
            out.append(". ");
            out.append(this.menu[index].getString());
            out.append(System.lineSeparator());
        }
        return out.toString();
    }



    /**
     * Действие по умолчанию, для пункта меню.
     */
    private static class ErrorAction implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
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
        private class EmptyAction implements TrackerAction {
            @Override
            public void execute(Input input, Tracker tracker) {
                input.println("Действие на этот пункт не реализвано!");
            }
        }
    }
}




