package ru.job4j.collection.pro.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 29.10.2017
 */
public class FastSetTest {

    /**
     * Test method.
     */
    @Test
    public void whenFullTestThenOk() {
        FastSet<String> set = new FastSet<>();
        set.add("first");
        set.add("second");
        set.add("third");
        set.add("fourth");
        set.add("fifth");
        Iterator iterator = set.iterator();
        String result = "";
        while (iterator.hasNext()) {
            result += iterator.next();
            iterator.hasNext();
        }
        assertThat(result, is("firstsecondthirdfourthfifth"));
    }

}