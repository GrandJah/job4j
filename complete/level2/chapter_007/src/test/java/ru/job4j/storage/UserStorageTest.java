package ru.job4j.storage;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.12.2017
 */
public class UserStorageTest {
    /** Вcе вместе до кучи.
     * @throws InterruptedException */
    @Test
    public void  whenFullTestThenAllOk() throws InterruptedException {
        UserStorage storage = new UserStorage();
        final long[] capacity = {0, 0};
        Thread adder = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                int amount = am();
                if (storage.add(new User(di(), amount))) {
                    capacity[0] += amount;
                }
            }
        });
        Thread deleter = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                User del = storage.getUser(di());
                if (del != null) {
                    int amount = del.getAmount();
                    if (storage.delete(del)) {
                        capacity[1] += amount;
                    }
                }
            }
        });
        Thread transfer = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                storage.transfer(di(), di(), am());
            }
        });
        adder.start();
        deleter.start();
        transfer.start();
        adder.join();
        deleter.join();
        transfer.join();
        assertThat(capacity[0] - capacity[1], is(storage.getCapacity()));
    }

    /** @return целое до 1 000*/
    private int di() {
        return getRand(1000);
    }

    /** @return целое до 1 000 000*/
    private int am()  {
        return getRand(1000000);
    }

    /** генератор случайных чисел.
     * @param range диапазон
     * @return случайное целое
     */
    private int getRand(int range) {
        return (int) (Math.random() * range);
    }



}