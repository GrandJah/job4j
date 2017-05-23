package ru.job4j.array;

/**
 * job4j.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 22.05.2017
 */
public class RotateArray {
    /**Поворачивает массив по часовой стрелке.
     * создает новый массив, если исходный массив не квадратный
     * @param array двумерный массив
     * @return повернутый массив по часовой стрелке
     */
    public int[][] rotate(int[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        int[][] returnArray;
        if (rows == cols) {
            returnArray = rotateSquareArray(array);
        } else {
            returnArray = new int[cols][rows];
            for (int n = 0; n < cols; n++) {
                for (int m = 0; m < rows; m++) {
                    returnArray[n][m] = array[rows - 1 - m][n];
                }
            }
        }
        return returnArray;
    }
    /** Поворачивает квадратный массив.
     * @param array исходный массив
     * @return повернутый массив
     */
    private int[][] rotateSquareArray(int[][] array) {
        int maxIndex = array.length - 1;
        for (int indexM = 0; indexM < array.length / 2 + array.length % 2; indexM++) {
            for (int indexN = 0; indexN < array.length / 2; indexN++) {
                int tmp = array[indexM][indexN];
                array[indexM][indexN] = array[maxIndex - indexN][indexM];
                array[maxIndex - indexN][indexM] = array[maxIndex - indexM][maxIndex - indexN];
                array[maxIndex - indexM][maxIndex - indexN] = array[indexN][maxIndex - indexM];
                array[indexN][maxIndex - indexM] = tmp;
            }
        }
        return array;
    }
}
