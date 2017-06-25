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
    private int index = -1;

    /**
     * Флаг нициализации.
     */
    private boolean init = false;

    /**
     * @param value Массив.
     */
    public IteratorEven(int[] value) {
        this.value = value;
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

    /**
     * Проверка инициализации индекса.
     */
    private void checkInit() {
        if (!this.init) {
            this.init = true;
            findNext();
        }
    }


    @Override
    public boolean hasNext() {
        checkInit();
        return this.index < this.value.length;
    }

    @Override
    public Object next() {
        checkInit();
        int ret = this.value[this.index];
        findNext();
        return ret;
    }
}
