package ru.job4j.collection.pro;

import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 18.06.2017
 */
public class IteratorArrayTwo implements Iterator {
    /**
     * Массив.
     */
    private int[][] value;
    /**
     * Индексы.
     */
    private int indexN, indexM;

    /**
     * @param value Иассив
     */
    public IteratorArrayTwo(int[][] value) {
        this.value = value;
        this.indexM = 0;
        this.indexN = 0;
    }

    @Override
    public boolean hasNext() {
        return this.indexN < this.value.length && value[0].length > 0;
    }

    @Override
    public Object next() {
        int ret = this.value[this.indexN][this.indexM++];
        if (this.indexM >= this.value[0].length) {
            this.indexN++; this.indexM = 0;
        }

        return ret;
    }
}
