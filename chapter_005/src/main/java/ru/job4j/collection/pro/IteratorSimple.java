package ru.job4j.collection.pro;

import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 19.06.2017
 */
public class IteratorSimple implements Iterator {

    /**
     * Массив.
     */
    private int[] value;

    /**
     * Индекс.
     */
    private int index;

    /**
     * @param value Массив.
     */
    public IteratorSimple(int[] value) {
        this.value = value;
        this.index = -1;
        findNext();
    }

    /**
     * Поиск следующего четного числа.
     */
    private void findNext() {
        for (this.index++; this.index < this.value.length; this.index++) {
            if (isPrime(this.value[this.index])) {
                break;
            }
        }
    }

    /**
     * Является ли число простым?
     *
     * @param n проверяемое число
     * @return число простое
     */
    private boolean isPrime(int n) {
        boolean prime = false;
        if (n == 1 || n == 2) {
            prime = true;
        } else if (n > 2) {
            prime = true;
            for (int i = 2; i * i <= n; i++) {
                if (n % i == 0) {
                    prime = false;
                    break;
                }
            }
        }
        return prime;
    }


    @Override
    public boolean hasNext() {
        return this.index < this.value.length;
    }

    @Override
    public Object next() {
        int ret = this.value[this.index];
        findNext();
        return ret;
    }
}

