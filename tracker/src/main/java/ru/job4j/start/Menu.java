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
     * размер меню.
     */
    private int size = 0;
    /**
     * User menu.
     */
    private TrackerAction[] menu;

    /**
     * Флаг выхода.
     */
    private boolean exit = false;

    /**
     * @return флаг выхода
     */
    boolean getExit() {
        return this.exit;
    }

    /**
     * Конструктор Меню.
     */
    Menu() {
        menu = new TrackerAction[8];
        addAction(new Add());
        addAction(new GetAll());
        addAction(new Edit());
        addAction(new Delete());
        addAction(new FindById());
        addAction(new FindByName());
        addAction(new Exit());
    }

    /** Выполенение пункта меню.
     * @param index пункт меню
     * @return действие
     */
    TrackerAction getAction(int index) {
        TrackerAction action;
        if (index < this.size && index >= 0) {
            action = this.menu[index];
        } else {
            action = new Menu.ErrorAction();
        }
        return action;
    }

    /** Добавление пункта в меню.
     * @param action Дейсвтие
     */
    private void addAction(TrackerAction action) {
        this.menu[this.size++] = action;
    }

    /**
     * Вывод полного меню.
     * @return Меню
     */
    String printMenu() {
        StringBuilder out = new StringBuilder();
        for (int index = 0; index < this.size; index++) {
            out.append(index);
            out.append(". ");
            out.append(this.menu[index].getInfo());
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

        @Override
        public String getInfo() {
            return "Ошибка";
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
            this.action = this.new EmptyAction();
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

            @Override
            public String getInfo() {
                return "Пустой пункт";
            }
        }
    }

    /**
     * Вывод всех элементов.
     */
    class GetAll implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            Item[] items = tracker.getAll();
            for (Item item : items) {
                input.println(item.toString());
            }
        }

        @Override
        public String getInfo() {
            return "Show all items";
        }
    }

    /**
     * Добавление.
     */
    class Add implements TrackerAction {
        @Override
        public String getInfo() {
            return "Add new Item";
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Введите ваше имя: ");
            String description = input.ask("Введите описание заявки: ");
            tracker.add(new Item(name, description));
        }
    }

    /**
     * Удаление.
     */
    class Delete implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            tracker.delete(tracker.findById(input.ask("Введите идентификатор:")));
        }

        @Override
        public String getInfo() {
            return "Delete item";
        }
    }

    /**
     * Редактирование.
     */
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

        @Override
        public String getInfo() {
            return "Edit item";
        }
    }

    /**
     * Выход.
     */
    class Exit implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            input.println("Программа завершена");
            exit = true;
        }

        @Override
        public String getInfo() {
            return "Exit Program";
        }
    }

    /**
     * Поиск по id.
     */
    class FindById implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            input.println(tracker.findById(input.ask("Введите идентификатор:")).toString());
        }

        @Override
        public String getInfo() {
            return "Find item by Id";
        }
    }

    /**
     * Поиск по имени.
     */
    class FindByName implements TrackerAction {
        @Override
        public void execute(Input input, Tracker tracker) {
            input.println(tracker.findByName(input.ask("Введите имя:")).toString());
        }

        @Override
        public String getInfo() {
            return "Find items by name";
        }
    }

}




