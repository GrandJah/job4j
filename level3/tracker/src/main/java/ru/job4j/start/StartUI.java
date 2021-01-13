package ru.job4j.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.job4j.tracker.Tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 25.05.2017
 */
@Component
public class StartUI {
    /**
     * Ввод-вывод.
     */
    @Autowired
    private Input input;

    /**
     * Трэкер заявок.
     */
    @Autowired
    private Tracker tracker;

    /**
     * Меню.
     */
    @Autowired
    private Menu menu;

    /**
     * @param tracker трэкер
     * @param input   интерфейс пользователя
     */
    public static StartUI of(Tracker tracker, Input input) {
        StartUI instance = new StartUI();
        instance.input = input;
        instance.tracker = tracker;
        instance.menu = new Menu();
        return instance;
    }

    /**
     * Application body.
     */
    public void run() {
        TrackerAction action = null;
        do {
            this.input.println(this.menu.toString());
            try {
                action = this.menu.selectAction(input);
                action.execute(this.input, this.tracker);
            } catch (MenuOutException ex) {
                this.input.println(ex.getMessage());
            } catch (Tracker.ErrorValue errorValue) {
                this.input.println("Ошибка ввода данных");
            } catch (Tracker.NotFound notFound) {
                this.input.println("Заявка не найдена.");
            }
        } while (!(action instanceof Menu.Exit));
    }
}
