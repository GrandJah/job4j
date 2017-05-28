package ru.job4j.start;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 25.05.2017
 */
public class ConsoleInput implements Input {
    /**
     * Ввод.
     */
    private Scanner input;
    /**
     * Вывод.
     */
    private PrintStream out;

    /**
     * default constructor.
     */
    ConsoleInput() {
        this.input = new Scanner(System.in);
        this.out = System.out;
    }

    /** Запрос с консоли.
     * @param question вопрос
     * @return ответ с консоли
     */
    @Override
    public String ask(String question) {
        print(question);
        return input.nextLine();
    }

    @Override
    public int ask(String question, int range) {
        throw new RuntimeException("ConsoleInput.ask - not realisation");//все равно перекрываем
    }

    /** Вывод данных в консоль.
     * @param data данные
     */
    @Override
    public void print(String data) {
        this.out.print(data);
    }

    /** Вывод с символом новой строки.
     * @param data данные
     */
    @Override
    public void println(String data) {
        this.out.println(data);
    }
}
