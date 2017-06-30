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
public class Node<T> {
    /**
     * Значение.
     */
    private T value;
    /**
     * Следующий элемент списка.
     */
    private Node<T> next;

    /**
     * @param value Значение
     */
    public Node(T value) {
        this.value = value;
    }

    /**
     * Getter.
     *
     * @return next next
     */
    public Node<T> getNext() {
        return this.next;
    }

    /**
     * Setter.
     *
     * @param next next
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * @param first Первая нода проверяемой цепи
     * @return наличие замыканий
     */
    boolean hasCycle(Node first) {
        boolean has = false;
        Node check = first;
        while (check != null) {
            Node next = check.next;
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
