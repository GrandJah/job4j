package ru.job4j.threadpool;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 10.12.2017
 */
@ThreadSafe
public class ThreadPool {
    /**
     * Очередь задач на выполнение.
     */
    @GuardedBy("this.works")
    private final LinkedList<Work> works;

    /**
     * Default.
     */
    public ThreadPool() {
        this.works = new LinkedList<>();

        for (int i = Runtime.getRuntime().availableProcessors(); i > 0; i--) {
            new Thread(() -> {
                Work work;
                while (true) {
                    synchronized (works) {
                        while (works.size() == 0) {
                            try {
                                works.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        work = works.removeFirst();
                    }
                    work.doWork();
                }
            }).start();
        }
    }

    /**
     * Добавление задачи на выполнение.
     *
     * @param work задача
     */
    public void add(Work work) {
        synchronized (this.works) {
            this.works.add(work);
            this.works.notify();
        }
    }
}
