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
    private final LinkedList<Work> works = new LinkedList<>();

    /** Список потоков. */
    private LinkedList<Thread> threads = new LinkedList<>();

    /** активность пула. */
    private boolean active = false;

    /** @return active активность пула. */
    public boolean isActive() {
        return this.active;
    }

    /** Инициализация пула. */
    private void init() {
        for (int i = Runtime.getRuntime().availableProcessors(); i > 0; i--) {
            threads.add(new Thread(() -> {
                Work work;
                while (true) {
                    if (!this.active) {
                        break;
                    }
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
            }));
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

    /** Остановка пула. */
    public void stop() {
        this.active = false;
        synchronized (this.works) {
            this.works.notify();
        }
        this.threads.clear();
    }

    /** Прерывание работы пула. */
    public void stopNow() {
        this.active = false;
        for (Thread thread: this.threads) {
            thread.interrupt();
        }
        this.threads.clear();
    }

    /** Пуск пула. */
    public void start() {
        if (!this.active) {
            this.active = true;
            init();
            for (Thread thread : this.threads) {
                thread.start();
            }
        }
    }
}
