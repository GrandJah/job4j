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
 * @since 21.08.2017
 */
public class LinkedSetTest {
    /**
     * Test method.
     */
    @Test
    public void whenFullTestThenOk() {
        LinkedSet<String> set = new LinkedSet<>();
        set.add("first");
        set.add("second");
        set.add("third");
        set.add("fourth");
        set.add("fourth");
        set.add("second");
        set.add("fifth");
        Iterator iterator = set.iterator();
        String result = "";
        while (iterator.hasNext()) {
            result += iterator.next();
            iterator.hasNext();
        }
        assertThat(result, is("fifthfourththirdsecondfirst"));
    }

}