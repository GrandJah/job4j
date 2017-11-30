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
    private int counter = 0;

    /** @return текущее состояние счетчика.*/
    @GuardedBy("this")
    Integer get() {
        synchronized (this) {
            return this.counter;
        }
    }

    /** Счетчик вызовов.
     * @return новоее состояние счетчика */
    @GuardedBy("this")
    int increment() {
        synchronized (this) {
            return ++this.counter;
        }
    }
}
