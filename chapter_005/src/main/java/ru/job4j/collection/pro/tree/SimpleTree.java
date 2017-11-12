package ru.job4j.collection.pro.tree;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 05.11.2017
 * @param <E> impl Comparable
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     * @param parent parent.
     * @param child child.
     * @return успешность добавления
     */
    boolean add(E parent, E child);
}
