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
        this.index = findNext(-1, value);
    }

    /**
     * Поиск следующего четного числа.
     * @param startIndex начальный индекс поиска в массиве
     * @param array массив в котором идет поиск
     * @return индекс элемента.
     */

    private int findNext(int startIndex, int[] array) {
        for (startIndex++; startIndex < array.length; startIndex++) {
            if (array[startIndex] % 2 == 0) {
                break;
            }
        }
        return startIndex;
    }


    @Override
    public boolean hasNext() {
        return this.index < this.value.length;
    }

    @Override
    public Object next() {
        int ret = this.value[this.index];
        this.index = findNext(this.index, this.value);
        return ret;
    }
}
