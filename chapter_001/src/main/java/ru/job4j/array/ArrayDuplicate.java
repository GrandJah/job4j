package ru.job4j.array;

import java.util.Arrays;

/**
 * job4j.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 22.05.2017
 */
public class ArrayDuplicate {
    /**
     * @param array исходный массив
     * @return массив без дубликатов
     */
    public String[] remove(String[] array) {
        int length = array.length;
        for (int index = 0; index < length; index++) {
            String element = array[index];
            for (int search = index + 1; search < length; search++) {
                if (element.equals(array[search])) {
                    array[search] = array[length - 1];
                    length--;
                    search--;
                }
            }
        }
        return Arrays.copyOf(array, length);
    }
}
