package ru.job4j.paint;

/**
 * Квадрат.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 26.05.2017
 */
public class Square implements Shape {
    @Override
    public String pic() {
        StringBuilder out = new StringBuilder();
        out.append("XXX");
        out.append(System.lineSeparator());
        out.append("XXX");
        out.append(System.lineSeparator());
        out.append("XXX");
        out.append(System.lineSeparator());
        return out.toString();
    }
}
