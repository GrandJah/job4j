package ru.job4j.merge;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 02.06.2017
 */
public class MergeArray {
    /** Сливает два массива в один.
     * @param firstArray сортированный массив
     * @param secondArray сортированный массив
     * @return итоговый сортированный массив.
     */
    public static int[] merge(int[] firstArray, int[] secondArray) {
        int[] returnArray = new int[firstArray.length + secondArray.length];
        int firstArrayPosition = 0, secondArrayPosition = 0;
        for (int index = 0; index < returnArray.length; index++) {
            if (firstArrayPosition == firstArray.length) {
                returnArray[index] = secondArray[secondArrayPosition];
                System.arraycopy(secondArray, secondArrayPosition, returnArray, index, returnArray.length - index);
                break;
            } else if (secondArrayPosition == secondArray.length) {
                System.arraycopy(firstArray, firstArrayPosition, returnArray, index, returnArray.length - index);
                break;
            } else {
                int first = firstArray[firstArrayPosition];
                int second = secondArray[secondArrayPosition];
                if (first < second) {
                    returnArray[index] = first;
                    firstArrayPosition++;
                } else {
                    returnArray[index] = second;
                    secondArrayPosition++;
                }
            }
        }
        return returnArray;
    }
}
