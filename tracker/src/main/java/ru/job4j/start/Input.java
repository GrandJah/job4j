package ru.job4j.start;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 25.05.2017
 */
public interface Input {
    /** Запрос.
     * @param question вопрос
     * @return ответ
     */
    String ask(String question);

    /** Вывод.
     * @param data данные
     */
    void print(String data);

    /** Вывод с концом строки.
     * @param data данные
     */
    void println(String data);


}
