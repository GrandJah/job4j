package ru.job4j.string_mathing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test class.
 */
public class MatchTest {

    /** main test method.
     * @param a string a
     * @param b string b
     * @param expected expect status compare
     */
    private void test(String a, String b, boolean expected) {
        Match math = new Match();
        assertEquals(expected, math.compareChar(a, b));
        assertEquals(expected, math.compareList(a, b));
    }

    /**
     * test full.
     */
    @Test
    public void testing() {
        test("mama", "amam", true);
        test("mama", "mam", false);
        test("mama", "ama", false);
        test("mamm", "aaam", false);
        test("mama", "amma", true);
        test("maa", "amam", false);

    }



}