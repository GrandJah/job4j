package ru.job4j.collection.pro.tree;
/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 12.11.2017
 * @param <E> тип знечений дерева
 */
public class BST<E extends Comparable<E>> {

    /**
     * Дочерние узлы.
     */
    private BST<E> left, right;

    /**
     * Значение узла.
     */
    private E value;

    /** Добавление елемента в дерево.
     * @param element добавляемый элемент
     * @return успешность добавления
     */
    public boolean add(E element) {
        boolean success;
        if (this.value == null) {
            this.value = element;
            this.left = new BST<>();
            this.right = new BST<>();
            success = true;
        } else {
            success = element.compareTo(this.value) > 0
                    ? this.right.add(element)
                    : this.left.add(element);
        }
        return success; //всегда true
    }

    @Override
    public String toString() {

        final StringBuilder sb = new StringBuilder();
        if (this.value != null) {
            sb.append('<');
            if (!this.left.toString().equals("")) {
                sb.append(this.left);
            }
            sb.append(this.value);
            if (!this.right.toString().equals("")) {
                sb.append(this.right);
            }
            sb.append('>');
        }
        return sb.toString();
    }
}
