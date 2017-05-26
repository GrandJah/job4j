package ru.job4j.paint;

/**
 * Треугольник.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 26.05.2017
 */
public class Triangle implements Shape {
    @Override
    public String pic() {
        StringBuilder out = new StringBuilder();
        out.append("X");
        out.append(System.lineSeparator());
        out.append("XX");
        out.append(System.lineSeparator());
        out.append("XXX");
        out.append(System.lineSeparator());
        return out.toString();
    }
}
