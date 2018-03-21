package ru.job4j.collection.pro.tree;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 12.11.2017
 */
public class TreeTest {
    /**
     * Test method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void whenTreeEmptyThenFail() {
        Tree<String> tree = new Tree<>(null);
        tree.add("lk", "lo");
    }

    /**
     * Test method.
     */
    @Test
    public void whenTreeNodeAddThenOk() {
        Tree<String> tree = new Tree<>("lk");
        assertThat(tree.add("lk", "lo"), is(true));
    }

    /**
     * Test method.
     */
    @Test
    public void whenTreeNodeAddThenFail() {
        Tree<String> tree = new Tree<>("lk");
        assertThat(tree.add("lk", "lk"), is(false));
    }

    /**
     * Test method.
     */
    @Test
    public void whenTreeManyAddThenOk() {
        Tree<String> tree = testTree();
        assertThat(tree.toString(),
                is("Tree: --lk[--lo[--ld[--lc[--lq[--qo[]]]], --la[]], --ln[--go[], --bo[]]]"));
    }

    /**
     * Test method.
     */
    @Test
    public void whenIteratorThenReturnOrderOk() {
        Tree<String> tree = testTree();
        Iterator<String> iterator = tree.iterator();
        final StringBuilder sb = new StringBuilder();
        for (String str : tree) {
            sb.append(str);
        }
        assertThat(sb.toString(),
                is("lkloldlclqqolalngobo"));

    }

    /**
     * Test method.
     */
    @Test
    public void whenTreeIsBinaryThenTrue() {
        Tree<String> tree = testTree();
        assertThat(tree.isBinary(), is(true));
    }

    /**
     * Test method.
     */
    @Test
    public void whenTreeNotBinaryThenFalse() {
        Tree<String> tree = testTree();
        tree.add("lk", "ho");
        assertThat(tree.isBinary(), is(false));
    }


    /**
     * @return тестовое дерево
     */
    private Tree<String> testTree() {
        Tree<String> tree = new Tree<>("lk");
        tree.add("lk", "lo");
        tree.add("lk", "ln");
        tree.add("ln", "go");
        tree.add("ln", "bo");
        tree.add("lo", "ld");
        tree.add("lo", "la");
        tree.add("ld", "lc");
        tree.add("lc", "lq");
        tree.add("lq", "qo");
        return tree;
    }
}