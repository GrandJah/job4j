package ru.job4j.loop;

/**
 * job4j.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.05.2017
 */
public class Counter {
    /**
     * @param start диапазо от
     * @param finish диапазон до
     * @return сумма четных чисел
     */
    public int add(int start, int finish) {
        int summa = 0;
        for (int i = start; i <= finish; i++) {
           if (i % 2 == 0) {
               summa += i;
           }
        }
        return summa;
    }
}
