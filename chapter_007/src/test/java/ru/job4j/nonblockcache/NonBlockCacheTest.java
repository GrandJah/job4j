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
    @Test
    public void whenExpectExceptionThenException() throws Exception {
        NonBlockCache cache = new NonBlockCache();
        cache.add(0, new TestModel());
        AtomicBoolean check = new AtomicBoolean(false);

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println(cache.get(0) + " thread2 - update");
                    cache.update(0, cache.get(0));
                    System.out.println(" thread2 - update");
                    System.out.println();
                } catch (OptimisticException e) {
                    System.out.println("thread2 - opl");
                    check.set(true);
                }
            }
        }).start();
        new Thread(() -> {
            TestModel model = (TestModel) cache.get(0);
            System.out.println(model + " thread1");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(model + " thread1 - update");
                cache.update(0, model);
                System.out.println(model + " thread1 - update");
            } catch (OptimisticException e) {
                System.out.println("thread1 - opl");
                check.set(true);
            }
        }).start();

        Thread.sleep(1200);

        assertThat(true, is(check));
    }
}

/**
 * Test Model.
 */
class TestModel extends NonBlockCache.Model {

}
