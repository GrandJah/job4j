package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.11.2017
 */
@ThreadSafe
class Count {
    /** Переменная счетчика. */
    @GuardedBy("this")
    private int counter = 0;

    /** @return текущее состояние счетчика.*/
    Integer get() {
        synchronized (this) {
            return this.counter;
        }
    }

    /** Счетчик вызовов.
     * @return новоее состояние счетчика */
    int increment() {
        synchronized (this) {
            return ++this.counter;
        }
    }
}
