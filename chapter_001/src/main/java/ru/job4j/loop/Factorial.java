package ru.job4j.loop;

/**
 * job4j.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.05.2017
 */
public class Factorial {
    /**
     * @param n целое число
     * @return факториал
     */
    public int calc(int n) {
        int factorial = 1;
        if (n > 1) {
            for (int i = 1; i <= n; i++) {
                factorial *= i;
            }
        }
        return factorial;
    }
}
