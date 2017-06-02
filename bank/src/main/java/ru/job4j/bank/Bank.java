package ru.job4j.bank;

import java.util.Arrays;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class Bank {
    /**
     * Посетители за день.
     */
    private Client[] clients;

    /**
     * @param clients Посетители за день
     */
    public Bank(Client[] clients) {
        this.clients = clients;
    }

    /** Возвращает диапазоны во время которых
     * было максимальное кол-во посетителей.
     * @return массив диапазонов
     */
    public Time[][] getRangeMaxClient() {
        int clientSize = this.clients.length;
        int[] in = new int[clientSize];
        int[] out = new int[clientSize];

        for (int i = 0; i < clientSize; i++) {
            in[i] = this.clients[i].getIn().getTime();
            out[i] = this.clients[i].getOut().getTime();
        }

        Arrays.sort(in);
        Arrays.sort(out);

        int[] timeLine = new int[clientSize * 2];
        int[] clientInBank = new int[clientSize * 2];

        int counter = 0, maxCounter = 0, inPosition = 0, outPosition = 0;
        for (int i = 0; i < timeLine.length; i++) {
            if (inPosition == clientSize) {
                timeLine[i] = out[outPosition++];
                clientInBank[i] = --counter;
            } else {
                int inTime = in[inPosition];
                int outTime = out[outPosition];
                if (inTime < outTime) {
                    timeLine[i] = inTime;
                    clientInBank[i] = ++counter;
                    maxCounter = maxCounter < counter ? counter : maxCounter;
                    inPosition++;
                } else {
                    timeLine[i] = outTime;
                    clientInBank[i] = --counter;
                    outPosition++;
                }
            }
        }

        Time[][] returnRanges = new Time[0][2];
        for (int i = 0; i < timeLine.length; i++) {
            if (clientInBank[i] == maxCounter) {
                Time[][] tempArray = new Time[returnRanges.length + 1][2];
                System.arraycopy(returnRanges, 0, tempArray, 0, returnRanges.length);
                tempArray[returnRanges.length] = new Time[]{new Time(timeLine[i]), new Time(timeLine[i + 1])};
                returnRanges = tempArray;
            }
        }

        return returnRanges;
    }
}
