package ru.job4j.collection.pro.list;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.06.2017
 *
 * @param <T> Тип
 */
public class CycleNode<T> {
    /**
     * Значение.
     */
    private T value;
    /**
     * Следующий элемент списка.
     */
    private CycleNode<T> next;

    /**
     * @param value Значение
     */
    CycleNode(T value) {
        this.value = value;
    }

    /**
     * Getter.
     *
     * @return next next
     */
    public CycleNode<T> getNext() {
        return this.next;
    }

    /**
     * Setter.
     *
     * @param next next
     */
    public void setNext(CycleNode<T> next) {
        this.next = next;
    }

    /**
     * @param first Первая нода проверяемой цепи
     * @return наличие замыканий
     */
    boolean hasCycle(CycleNode first) {
        boolean has = false;
        CycleNode check = first;
        while (check != null) {
            CycleNode next = check.next;
            while (next != null) {
                if (next == check) {
                    has = true;
                    break;
                }
                next = next.next;
            }
            if (has) {
                break;
            }
            check = check.next;
        }
        return has;
    }
}
