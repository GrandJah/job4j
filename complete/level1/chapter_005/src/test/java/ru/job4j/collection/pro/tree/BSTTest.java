package ru.job4j.collection.pro.tree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 12.11.2017
 */
public class BSTTest {
    /**
     * Test method.
     */
    @Test
    public void whenAddthenCorrect() {
        BST<String> tree = new BST<>();
        tree.add("ln");
        tree.add("lo");
        tree.add("ln");
        tree.add("go");
        tree.add("bo");
        tree.add("ld");
        tree.add("ln");
        tree.add("ln");
        tree.add("la");
        tree.add("lc");
        tree.add("lq");
        tree.add("qo");
        tree.add("ld");
        assertThat(tree.toString(), is("<<<<bo>go<<la<lc<ld>>>ld<<ln>ln>>>ln>ln<lo<lq<qo>>>>"));
    }
}