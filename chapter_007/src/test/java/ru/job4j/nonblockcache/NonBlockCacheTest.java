package ru.job4j.nonblockcache;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.12.2017
 */
public class NonBlockCacheTest {
    /**
     * Test method.
     * @throws OptimisticException -
     */
    @Test
    public void whenFullThenOk() throws OptimisticException {

        NonBlockCache cache = new NonBlockCache();
        for (int i = 0; i < 100; i++) {
            cache.add(i, new TestModel());
        }

        for (int i = 0; i < 50; i++) {
            cache.delete(i, cache.get(i));
        }

        for (int i = 50; i < 100; i++) {
            cache.update(i, cache.get(i));
        }
    }

    /**
     * Test method.
     * @throws Exception -
     */
//    @Test
    public void whenExpectExceptionThenException() throws Exception {
        NonBlockCache cache = new NonBlockCache();
        cache.add(0, new TestModel());
        AtomicBoolean check = new AtomicBoolean(false);

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    cache.update(0, cache.get(0));
                    System.out.println();
                } catch (OptimisticException e) {
                    check.set(true);
                }
            }
        }).start();
        new Thread(() -> {
            TestModel model = (TestModel) cache.get(0);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                cache.update(0, model);
            } catch (OptimisticException e) {
                check.set(true);
            }
        }).start();

        Thread.sleep(10);

        assertThat(check.get(), is(true));
    }
}

/**
 * Test Model.
 */
class TestModel extends NonBlockCache.Model {

}