package ru.job4j.collection.pro.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 12.11.2017
 * @param <E> тип элементов
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    /**
     * Узел дерева.
     */
    class Node {
        /**
         * Список дочерних узлов поддерева.
         */
        private List<Node> children;
        /**
         * Значение узла.
         */
        private E value;

        /**
         * @param value значение узла.
         */
        Node(E value) {
            if (value == null) {
                throw new IllegalArgumentException();
            }
            this.value = value;
            this.children = new LinkedList<>();
        }

        /** Поиск узла по значению элемента.
         * @param element элемент
         * @return найденный узел или null
         */
        Node find(E element) {
            Node find = null;
            if (this.value.compareTo(element) == 0) {
                find = this;
            } else {
                for (Node node : this.children) {
                    Node loopNode = node.find(element);
                    if (loopNode != null) {
                        find = loopNode;
                        break;
                    }
                }
            }
            return find;
        }

        /**
         * @return список элементов в поддереве.
         */
        List<E> getAllList() {
            List<E> ret = new LinkedList<>();
            ret.add(this.value);
            for (Node node :this.children) {
                ret.addAll(node.getAllList());
            }
            return ret;
        }

        @Override
        public String toString() {
            return "--" + value + children;
        }

        /**
         * @return true если дерево бинарное
         */
        boolean isBinary() {
            boolean bin = false;
            if (this.children.size() <= 2) {
                bin = true;
                if (this.children.size() != 0) {
                    for (Node node : this.children) {
                        bin = bin && node.isBinary();
                    }
                }
            }
            return bin;
        }
    }

    /**
     * Корневой узел дерева.
     */
    private Node root;

    /**
     * @param root корневой узел дерева.
     */
    public Tree(E root) {
        this.root = new Node(root);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean success = false;
        if (!contains(child)) {
            Node node = this.root.find(parent);
            if (node != null) {
                node.children.add(new Node(child));
                success = true;
            }
        }
        return success;
    }

    /**
     * @return true если дерево бинарное
     */
    public boolean isBinary() {
        return this.root.isBinary();
    }

    /** Поиск элемента в поддереве.
     * @param element искомый элемент
     * @return true ксли элемент присутсвует
     */
    private boolean contains(E element) {
        boolean contain = false;
        for (E e : this) {
            if (e.equals(element)) {
                contain = true;
                break;
            }
        }
        return contain;
    }

    @Override
    public Iterator<E> iterator() {
        return this.root.getAllList().iterator();
    }

    @Override
    public String toString() {
        return "Tree: " + this.root;
    }
}
