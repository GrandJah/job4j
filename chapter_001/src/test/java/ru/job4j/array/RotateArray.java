package ru.job4j.array;

/**
 * job4j.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 22.05.2017
 */
public class RotateArray {
    /**
     * @param array двумерный массив
     * @return повернутый массив по часовой стрелке
     */
    public int[][] rotate(int[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        int[][] returnArray = new int[cols][rows];
        for (int n = 0; n < cols; n++) {
            for (int m = 0; m < rows; m++) {
                returnArray[n][m] = array[rows - 1 - m][n];
            }
        }
        return returnArray;
    }
}
