package ru.job4j.start;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 27.05.2017
 */
public class StubInput implements Input {
    /**
     * Ответы пользователя.
     */
    private String[] answer;

    /**
     * текущий ответ.
     */
    private int currentAnswer = 0;

    /**
     * Вопросы пользователю.
     */
    private String[] out;

    /**
     * текущий ответ.
     */
    private int currentAsk = 0;

    /**
     * @param answer Массив ответов пользователя
     * @param outSize размер массива вывода системы
     */
    StubInput(String[] answer, int outSize) {
        this.answer = answer;
        this.out = new String[outSize];
    }

    /**
     * @return массив вывод системы.
     */
    String[] getOut() {
        return this.out;
    }

    @Override
    public String ask(String question) {
        print(question);
        return this.answer[currentAnswer++];
    }
    @Override
    public int ask(String question, int range) {
        return Integer.parseInt(ask(question));
    }

    @Override
    public void print(String data) {
        this.out[currentAsk++] = data;
    }

    @Override
    public void println(String data) {
        print(String.format("%s%s", data, System.lineSeparator()));
    }
}
