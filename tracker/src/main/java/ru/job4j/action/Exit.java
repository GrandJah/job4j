package ru.job4j.action;

/** Выход из приложения.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 25.05.2017
 */
public class Exit extends TrackerAction {
    public void execute() {
        getInput().println("Программа завершена.");
        System.exit(0);
    }
}
