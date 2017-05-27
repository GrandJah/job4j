package ru.job4j.start;

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
     */
    StartUI(Tracker tracker, Input input) {
        this.input = input;
        this.tracker = tracker;
        this.menu = new Menu();
    }

    /**
     * @param args аргументы
     */
    public static void main(String[] args) {
        StartUI startUI = new StartUI(new Tracker(), new ConsoleInput());
        startUI.run();
    }

    /**
     * Application body.
     */
    void run() {
        do {
            this.input.println(this.menu.printMenu());
            int answer = Integer.parseInt(this.input.ask("Select : "));
            TrackerAction action = this.menu.getAction(answer);
            action.execute(this.input, this.tracker);
        } while (!menu.getExit());
    }
}
