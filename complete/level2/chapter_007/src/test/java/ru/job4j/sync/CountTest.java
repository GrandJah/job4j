package ru.job4j.sync;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.11.2017
 */
public class CountTest {
    /** Test method.
     * @throws InterruptedException .*/
    @Test
    public void whenThen() throws InterruptedException {
        Count loop1 = new Count();
        Count loop2 = new Count();
        Count count = new Count();
        Thread thread1 = new Thread(() -> {
            while (loop1.increment() <= 500) {
                count.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            while (loop2.increment() <= 500) {
                count.increment();
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        assertThat(count.get(), is(1000));
    }
}