package ru.job4j.collection.pro.set;

import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 31.10.2017
 */
public class TimeTest {
    /**
     * Кол-во элементов в добавляемом массиве.
     */
    private int amount;
    /**
     * Тестовый массив для добавления.
     */
    private String[] arr;

    /**
     * @param amount число элементов в исходном массиве
     */
    public TimeTest(int amount) {
        this.amount = amount;
        this.arr = new String[amount];
        for (int i = 0; i < amount; i++) {
            arr[i] = Double.toString(Math.random()).substring(0, 5);
        }
    }

    /**Сравнение времени вставки.
     * @param args args
     */
    public static void main(String[] args) {
        TimeTest test = new TimeTest(10000);

        FastSet<String> fastSet = new FastSet<>();
        SimpleSet<String> simpleSet = new SimpleSet<>();
        LinkedSet<String> linkedSet = new LinkedSet<>();
        long start;

        start = System.currentTimeMillis();
        for (int i = 0; i < test.amount; i++) {
            fastSet.add(test.arr[i]);
        }
        long timeFast =  System.currentTimeMillis() - start;


        start = System.currentTimeMillis();
        for (int i = 0; i < test.amount; i++) {
            simpleSet.add(test.arr[i]);
        }
        long timeSimple =  System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        for (int i = 0; i < test.amount; i++) {
            linkedSet.add(test.arr[i]);
        }
        long timeLinked =  System.currentTimeMillis() - start;

        StringBuilder out = new StringBuilder();
        test.append(out, "FastSet - ", timeFast, test.getSize(fastSet.iterator()));
        test.append(out, "SimpleSet - ", timeSimple, test.getSize(simpleSet.iterator()));
        test.append(out, "LinkedSet - ", timeLinked, test.getSize(linkedSet.iterator()));

        System.out.println(out);
    }

    /**
     * @param out строка вывода
     * @param name имя класса
     * @param time время теста
     * @param size размер массива
     */
    private void append(StringBuilder out, String name, long time, int size) {
        out.append(name);
        out.append(time);
        out.append(" - ");
        out.append(size);
        out.append(System.lineSeparator());
    }

    /**
     * @param it итератор
     * @return размер итерируемого списка
     */
    private int getSize(Iterator it) {
        int size = 0;
        while (it.hasNext()) {
            it.next();
            size++;
        }
        return size;
    }
}
