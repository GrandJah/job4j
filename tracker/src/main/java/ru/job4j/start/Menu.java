package ru.job4j.start;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

import java.util.ArrayList;

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
    private ArrayList<TrackerAction> menu = new ArrayList<>();

    /**
     * Конструктор Меню.
     */
    Menu() {
        addAction(new BaseAction("Add new Item") {
            @Override
            public void execute(Input input, Tracker tracker) throws Tracker.ErrorValue {
                String name = input.ask("Введите ваше имя: ");
                String description = input.ask("Введите описание заявки: ");
                tracker.add(new Item(name, description));
            }
        });
        addAction(new BaseAction("Show all items") {
            @Override
            public void execute(Input input, Tracker tracker) {
                for (Item item : tracker.getAll()) {
                    input.println(item.toString());
                }
            }
        });
        addAction(new BaseAction("Edit item") {
            @Override
            public void execute(Input input, Tracker tracker) throws Tracker.NotFound, Tracker.ErrorValue {
                Item item = tracker.findById(input.ask("Введите идентификатор:"));
                if (item != null) {
                    String answer = input.ask("Введите новое имя:");
                    if (!answer.equals("")) {
                        item.setName(answer);
                    }
                    answer = input.ask("Введите новое описание:");
                    if (!answer.equals("")) {
                        item.setDescription(answer);
                    }
                    tracker.update(item);
                } else {
                    input.println("Заявка не найдена.");
                }
            }
        });
        addAction(new BaseAction("Delete item") {
            @Override
            public void execute(Input input, Tracker tracker) throws Tracker.NotFound, Tracker.ErrorValue {
                tracker.delete(tracker.findById(input.ask("Введите идентификатор:")));
            }
        });
        addAction(new BaseAction("Find item by Id") {
            @Override
            public void execute(Input input, Tracker tracker) throws Tracker.NotFound {
                input.println(tracker.findById(input.ask("Введите идентификатор:")).toString());
            }
        });
        addAction(new BaseAction("Find items by name") {
            @Override
            public void execute(Input input, Tracker tracker) throws Tracker.NotFound {
                input.println(tracker.findByName(input.ask("Введите имя:")).toString());
            }
        });
        addAction(new Exit());
    }

    /** Добавление пункта в меню.
     * @param action Дейсвтие
     */
    private void addAction(TrackerAction action) {
        this.menu.add(action);
    }

    /** Выбор пункта меню.
     * @param input Интерфейс пользователя
     * @return Действие
     */
    TrackerAction selectAction(Input input) {
        return this.menu.get(input.ask("Select : ", this.menu.size()));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int index = 0; index < this.menu.size(); index++) {
            out.append(index);
            out.append(". ");
            out.append(this.menu.get(index).getInfo());
            out.append(System.lineSeparator());
        }
        return out.toString();
    }

    /**
     * Выход.
     */
    class Exit extends BaseAction {
        /**
         * Default constructor.
         */
        Exit() {
            super("Exit Program");
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            input.println("Программа завершена");
        }
    }
}




