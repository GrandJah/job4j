package ru.job4j.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 10.12.2017
 */
public class MyLock implements Lock {

    /** Синхнронизатор. */
    private final Object lock = new Object();

    /** Активность блокировки. */
    private boolean locked = false;

    /** Поток взявший блокировку. */
    private Thread thread = null;

    @Override
    public void lock() {
        synchronized (this.lock) {
            while (this.locked) {
                try {
                    this.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.locked = true;
            this.thread = Thread.currentThread();
        }
    }

    @Override
    public void unlock() {
        synchronized (this.lock) {
            locked = false;
        }
        this.thread = null;
        this.lock.notify();
    }

    @Override
    public boolean tryLock() {
        boolean lock = false;
        synchronized (this.lock) {
            if (!this.locked) {
                this.locked = true;
                lock = true;
                this.thread = Thread.currentThread();
            }
        }
        return lock;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        boolean lock = false;
        long waitTime = System.currentTimeMillis() + unit.toMillis(time);
        synchronized (this.lock) {
            while (this.locked && waitTime > System.currentTimeMillis()) {
                this.lock.wait(waitTime - System.currentTimeMillis());
            }
            if (!this.locked) {
                this.locked = true;
                lock = true;
                this.thread = Thread.currentThread();
            }
        }
        return lock;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        synchronized (this.lock) {
            if (this.thread != null) {
                this.thread.interrupt();
                this.locked = false;
                this.notify();
            }
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
