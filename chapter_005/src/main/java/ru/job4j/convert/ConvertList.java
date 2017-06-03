package ru.job4j.convert;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class ConvertList {
    /** Добавление элементов массива в список.
     * @param array входящий массив
     * @return список элементов массива
     */
    public List<Integer> toList(int[][] array) {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                list.add(array[i][j]);
            }
        }
        return list;
    }

    /** равномерно разбивает список на количество строк двумерного массива.
     * В методе toArray должна быть проверка -
     *  если количество элементов не кратно количеству строк
     *  - оставшиеся значения в массиве заполнять нулями.
     * @param list входящий список
     * @param rows кол-во строк
     * @return двумерный массив
     */
    public int[][] toArray(List<Integer> list, int rows) {
        Iterator<Integer> iterator = list.iterator();
        int cols = list.size() / rows + (list.size() % rows == 0 ? 0 : 1);

        int[][] array = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (iterator.hasNext()) {
                    array[i][j] = iterator.next();
                } else {
                    array[i][j] = 0;
                }
            }
        }
        return array;
    }

    /** добавление всех значений массивов в общий список.
     * @param list список массивов
     * @return список значений
     */
    public List<Integer> convert(List<int[]> list) {
        List<Integer> newList = new LinkedList<>();
        for (int[] array:list) {
            for (int element : array) {
                newList.add(element);
            }
        }
        return newList;
    }
}
