package ru.job4j.compare;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.06.2017
 */
public class SortUserTest {
    /**
     * Test method.
     */
    @Test
    public void whenListToSetThenNewOrder() {
        SortUser sort = new SortUser();
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("sdfffs", 12));
        list.add(new User("ssdc", 15));
        list.add(new User("sdffddfs", 9));
        list.add(new User("adc", 8));
        list.add(new User("sdsadfffs", 45));
        list.add(new User("sdasdccfffs", 139));
        Set<User> set  = sort.sort(list);
        list.sort(null);
        assertThat(set.toArray(), is(list.toArray()));
    }
}