package ru.job4j.start;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 28.05.2017
 */
public class ValidateInput extends ConsoleInput {
    @Override
    public int ask(String question, int rangeMax) {
        try {
            int answer = Integer.parseInt(ask(question));
            if (answer >= rangeMax || answer < 0) {
                throw new MenuOutException("Неверный пункт меню");
            } else {
                return answer;
            }
        } catch (NumberFormatException e) {
            throw new MenuOutException("Неправильный ввод, введите число");
        }

    }
}
