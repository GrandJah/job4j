package ru.job4j.collection.pro.map;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 05.11.2017
 */
public class CatalogTest {

    /**
     * Test method.
     */
    @Test
    public void whenInsertDuplicateThenFalse() {
        Catalog<Integer, String> phoneBook = new Catalog<>(100);
        phoneBook.insert(135213, "Mama");
        boolean result = phoneBook.insert(135213, "Papa");

        assertThat(result, is(false));
    }

    /**
     * Test method.
     */
    @Test
    public void whenInsertVariedThenFalse() {
        Catalog<Integer, String> phoneBook = new Catalog<>(100);
        phoneBook.insert(135213, "Mama");
        boolean result = phoneBook.insert(135214, "Papa");

        assertThat(result, is(true));
    }

    /**
     * Test method.
     */
    @Test
    public void whenGetThenReturnElement() {
        Catalog<Integer, String> phoneBook = new Catalog<>(100);
        phoneBook.insert(135213, "Mama");
        phoneBook.insert(135214, "Papa");
        String result = phoneBook.get(135214);

        assertThat(result, is("Papa"));
    }

    /**
     * Test method.
     */
    @Test
    public void whenDeleteThenCorrectDelete() {
        Catalog<Integer, String> phoneBook = new Catalog<>(100);
        phoneBook.insert(135213, "Mama");
        phoneBook.insert(135214, "Papa");
        boolean result = phoneBook.delete(135214);

        assertThat(result, is(true));
    }

    /**
     * Test method.
     */
    @Test
    public void whenDeleteThenCorrectDelete2() {
        Catalog<Integer, String> phoneBook = new Catalog<>(100);
        phoneBook.insert(135213, "Mama");
        phoneBook.insert(135214, "Papa");
        phoneBook.delete(135214);
        boolean result = phoneBook.get(135214) == null;

        assertThat(result, is(true));
    }

    /**
     * Test method.
     */
    @Test
    public void whenIteratorThenReturnCorrect() {
        Catalog<Integer, String> phoneBook = new Catalog<>(100);
        phoneBook.insert(135213, "Mama");
        phoneBook.insert(135218, "Papa");
        phoneBook.insert(135225, "Sister");
        phoneBook.insert(135225, "Brother");


        Iterator<Entry<Integer, String>> it = phoneBook.iterator();
        Entry<Integer, String> entry;

        int size = 0;
        int error = 0;

        while (it.hasNext()) {
            entry = it.next();
            if (entry == null || phoneBook.insert(entry.getKey(), entry.getValue())) {
                error++;
            }
            size++;
        }
        boolean result = error == 0 && size == 3;

        assertThat(result, is(true));
    }

    /**
     * Test method.
     */
    @Test(expected = NoSuchElementException.class)
    public void whenIteratorNextThenException() {
        Catalog<Integer, String> phoneBook = new Catalog<>(100);
        phoneBook.insert(135213, "Mama");
        phoneBook.insert(135218, "Papa");
        phoneBook.insert(135225, "Sister");
        phoneBook.insert(135228, "Brother");
        Iterator it = phoneBook.iterator();
        while (true) {
            it.next();
        }
    }

    /**
     * Test method.
     */
    @Test
    public void whenHashCodeNegativeThenOk() {
        Catalog<NegativeHash, String> phoneBook = new Catalog<>(100);
        assertThat(phoneBook.insert(new NegativeHash(), "Papa"), is(true));
    }

    /**
     * Help test class.
     */
    private class NegativeHash {
        @Override
        public int hashCode() {
            return -17;
        }
    }
}