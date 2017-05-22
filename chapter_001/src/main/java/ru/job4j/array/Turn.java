package ru.job4j.array;

/**
 * job4j.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 22.05.2017
 */
public class Turn {
    /**
     * @param array массив чисел
     * @return Перевернутый массив
     */
    public int[] back(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[array.length - 1 - i ];
            array[array.length - 1 - i] = array[i];
            array[i] = temp;
        }
        return array;
    }
}
