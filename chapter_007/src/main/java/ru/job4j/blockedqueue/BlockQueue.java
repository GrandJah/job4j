package ru.job4j.blockedqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;


/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 10.12.2017
 * @param <T>
 */
@ThreadSafe
public class BlockQueue<T> {
    /** Очередь. */
    @GuardedBy("this.queue")
    private final LinkedList<T> queue;
    /** Ограничение размера очереди. */
    private int sizeQueue;

    /** @param sizeQueue ограничение очереди */
    public BlockQueue(int sizeQueue) {
        this.queue = new LinkedList<>();
        this.sizeQueue = sizeQueue;
    }

    /**Вставить в очередь.
     * @param task элемент в очередь
     */
    public void put(T task) throws InterruptedException {
        synchronized (this.queue) {
                while (queue.size() >= this.sizeQueue) {
                    this.queue.wait();
                }
                this.queue.add(task);
                this.queue.notify();
        }
    }

    /** Вытащить из очереди.
     * @return элемент из очереди
     */
    public T take() throws InterruptedException {
        T task;
        synchronized (this.queue) {
                while (queue.size() == 0) {
                    this.queue.wait();
                }
                task = this.queue.removeFirst();
                this.queue.notify();
        }
        return task;
    }

    /** @return размер очереди*/
    public int size() {
        synchronized (this.queue) {
            return this.queue.size();
        }
    }

    /**@param args args*/
    public static void main(String[] args) {
        BlockQueue<String> queue = new BlockQueue<>(2);
        Thread[] producer = new Thread[5];
        Thread[] consumer = new Thread[7];
        for (int i = 0; i < producer.length; i++) {
            producer[i] = new Thread("producer " + i) {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep((long) (2100 + Math.random() * 700));
                            queue.put(getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            producer[i].start();
        }
        for (int i = 0; i < consumer.length; i++) {
            consumer[i] = new Thread("consumer " + i) {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep((long) (1500 + Math.random() * 500));
                            System.out.println(this.getName() + " - " + queue.take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            consumer[i].start();
        }
    }
}
