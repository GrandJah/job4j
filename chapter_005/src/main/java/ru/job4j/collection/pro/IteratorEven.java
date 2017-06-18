package ru.job4j.collection.pro;

import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 18.06.2017
 */
public class IteratorEven implements Iterator {

    /**
     * Массив.
     */
    private int[] value;

    /**
     *  Индекс.
     */
    private int index;

    /**
     * @param value Массив.
     */
    public IteratorEven(int[] value) {
        this.value = value;
        this.index = 0;
        findNext();
    }

    /**
     * Поиск следующего четного числа.
     */
    private void findNext() {
        for (this.index++; this.index < this.value.length; this.index++) {
            if (this.value[this.index] % 2 == 0) {
                break;
            }
        }
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
