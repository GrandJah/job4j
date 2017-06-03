package ru.job4j.convert;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class UserConvertTest {
    /**
     * Test method.
     */
    @Test
    public void whenListConvertThenMapReturn() {
        List<User> list = new LinkedList<>();
        list.add(new User("Коля", "Питер"));
        list.add(new User("Вася", "Катер"));
        list.add(new User("Саня", "Адлер"));
        HashMap<Integer, User> expect = new HashMap<>();
        expect.put(830877408, new User("Коля", "Питер"));
        expect.put(-1590790892, new User("Вася", "Катер"));
        expect.put(1288498114, new User("Саня", "Адлер"));
        UserConvert convert = new UserConvert();
        HashMap<Integer, User> result = convert.process(list);
        assertThat(result, is(expect));
    }
}