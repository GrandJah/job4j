package ru.job4j.array;

/**
 * job4j.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 22.05.2017
 */
public class BubbleSort {
    /**
     * @param array массив чисел
     * @return отсортированнй массив
     */
    public int[] sort(int[] array) {
        for (int right = array.length - 2; right >= 0; right--) {
            for (int index = 0; index <= right; index++) {
                if (array[index] > array[index + 1]) {
                    int temp = array[index];
                    array[index] = array[index + 1];
                    array[index + 1] = temp;
                }
            }
        }
        return array;
    }
}
