package ru.job4j.threadpool;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.junit.Test;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 14.12.2017
 */
public class ThreadPoolTest {
    /**
     * Test method.
     */
    @Test
    public void whenThreadPoolThenOk() {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            pool.add(new Work() {
                @Override
                public void doWork() {
                    while (true) {
                        getEnqueueTime();
                    }
                }

                @Override
                public void setEnqueueTime(long timeInMillis) {

                }

                @Override
                public long getEnqueueTime() {
                    return 0;
                }

                @Override
                public String getName() {
                    return null;
                }
            });
        }
        pool.start();
        pool.stop();
        pool.start();
        pool.stopNow();
        pool.start();
        pool.stop();
    }
}