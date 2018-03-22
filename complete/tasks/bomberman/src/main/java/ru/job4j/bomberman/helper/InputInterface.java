package ru.job4j.bomberman.helper;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.12.2017
 */
public interface InputInterface {
    /**
     * Карабкаемся вверх.
     */
    void goUP();

    /**
     * Спускаемся вниз.
     */
    void goDown();

    /**
     * Уходим налево.
     */
    void goLeft();

    /**
     * Берем правее.
     */
    void goRight();

    /**
     * Делай "раз".
     */
    void actionA();

    /**
     * Делай "Два".
     */
    void actionB();
}
