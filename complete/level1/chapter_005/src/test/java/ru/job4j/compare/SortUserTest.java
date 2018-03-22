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
     * @return заполненый список
     */
    private ArrayList<User> fillList() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("sdfffs", 12));
        list.add(new User("ssdc", 15));
        list.add(new User("sdffdds", 9));
        list.add(new User("adc", 8));
        list.add(new User("sdsadfffs", 45));
        list.add(new User("sdasdccs", 139));
        return list;
    }

    /**
     * Test method.
     */
    @Test
    public void whenListToSetThenNewOrder() {
        SortUser sort = new SortUser();
        ArrayList<User> list = fillList();
        Set<User> set  = sort.sort(list);
        list.sort(null);
        assertThat(set.toArray(), is(list.toArray()));
    }

    /**
     * Test method.
     */
    @Test
    public void whenListSortNameLengthThenNewOrder() {
        SortUser sort = new SortUser();
        ArrayList<User> list = fillList();
        int[] length = new int[list.size()];
        sort.sortLength(list);
        for (int i = 0; i < list.size(); i++) {
            length[i] = list.get(i).getName().length();
        }
        assertThat(length, is(new int[]{3, 4, 6, 7, 8, 9}));
    }

    /**
     * Test method.
     */
    @Test
    public void whenListSortHashCodeThenNewOrder() {
        SortUser sort = new SortUser();
        ArrayList<User> list = fillList();
        int[] length = new int[list.size()];
        sort.sortHash(list);
        for (int i = 0; i < list.size(); i++) {
            length[i] = list.get(i).hashCode();
        }
        assertThat(length, is(new int[]{2988904, 64805081, 109730064, 133231815, 227454843, 1943962734}));
    }



}