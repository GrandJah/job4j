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

    /**
     * @param question вопрос
     * @param rangeMax диапазон чисел для ответа [0 - rangeMax)
     * @return ответ-число
     */
    int ask(String question, int rangeMax);

    /** Вывод.
     * @param data данные
     */
    void print(String data);

    /** Вывод с концом строки.
     * @param data данные
     */
    void println(String data);


}
