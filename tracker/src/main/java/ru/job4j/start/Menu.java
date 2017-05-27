package ru.job4j.start;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 25.05.2017
 */
class Menu {
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
            new MenuItem("Exit Program", new Exit())
    };

    private boolean exit = false;

    /** Выполенение пункта меню.
     * @param index пункт меню
     * @return действие
     */
    TrackerAction getAction(int index) {
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
    String printMenu() {
        StringBuilder out = new StringBuilder();
        for (int index = 0; index < this.menu.length; index++) {
            out.append(index);
            out.append(". ");
            out.append(this.menu[index].getString());
            out.append(System.lineSeparator());
        }
        return out.toString();
    }

    boolean getExit() {
        return this.exit;
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
    class GetAll implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            Item[] items = tracker.getAll();
            for (Item item : items) {
                input.println(item.toString());
            }
        }
    }
    class Add implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Введите ваше имя: ");
            String description = input.ask("Введите описание заявки: ");
            tracker.add(new Item(name, description));
        }
    }
    class Delete implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            tracker.delete(tracker.findById(input.ask("Введите идентификатор:")));
        }
    }
    class Edit implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            Item item = tracker.findById(input.ask("Введите идентификатор:"));
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
    class Exit implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            input.println("Программа завершена");
            exit = true;
        }
    }
    class FindById implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            input.println(tracker.findById(input.ask("Введите идентификатор:")).toString());
        }
    }
    class FindByName implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            input.println(tracker.findByName(input.ask("Введите имя:")).toString());
        }
    }

}




