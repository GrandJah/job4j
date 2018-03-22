package xml_xslt_jdbs;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 25.05.2017
 */
public class Console {
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
    Console() {
        this.input = new Scanner(System.in);
        this.out = System.out;
    }

    /** Запрос с консоли.
     * @param question вопрос
     * @return ответ с консоли
     */
    public int ask(String question) {
        print(question);
        try {
            return Integer.valueOf(input.nextLine());
        } catch (NumberFormatException e) {
            println("Неверный ввод!");
            return 0;
        }
    }

    /** Вывод данных в консоль.
     * @param data данные
     */
    public void print(Object data) {
        this.out.print(data);
    }

    /** Вывод с символом новой строки.
     * @param data данные
     */
    public void println(Object data) {
        this.out.println(data);
    }
}
