package ru.job4j.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class TimeTest {
    /** Замер времени добавления в коллекцию.
     * @param collection коллекция
     * @param amount количество
     * @return время выполнения
     */
    private long add(Collection<String> collection, int amount) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            collection.add(Double.toString(Math.random()));
        }
        return System.currentTimeMillis() - start;
    }

    /** Замер времени удаления из коллекции.
     * @param collection коллекция
     * @param amount количество
     * @return время выполнения
     */
    private long delete(Collection<String> collection, int amount) {
        long start = System.currentTimeMillis();
        Iterator<String> iterator = collection.iterator();
        for (int i = 0; i < amount; i++) {
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
        return System.currentTimeMillis() - start;
    }

    /**
     * @param collection коллекция
     * @param size кол-во добавляемых элементов
     * @param deleteN кол-во удаляемых элементов
     */
    private void testCollection(Collection<String> collection, int size, int deleteN) {
        int add = (int) add(collection, size);
        int delete = (int) delete(collection, deleteN);
        System.out.println(String.format("%s : add - %d, delete - %d", collection.getClass(), add, delete));
    }

    /**
     * @param args параметры строки.
     */
    public static void main(String[] args) {
        TimeTest test = new TimeTest();
        test.testCollection(new LinkedList<>(), 100000, 50000);
        test.testCollection(new ArrayList<>(), 100000, 50000);
        test.testCollection(new TreeSet<>(), 100000, 50000);

    }
}
