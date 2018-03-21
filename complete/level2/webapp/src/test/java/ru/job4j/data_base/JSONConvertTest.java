package ru.job4j.data_base;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class JSONConvertTest {

    /**
     * Test method.
     */
    @Test
    public void toJSON() {
        assertEquals("{}", JSONConvert.EMPTY.toJSON());
    }

    /**
     * Test method.
     * @throws ParseException parse exception
     */
    @Test(expected = UnsupportedOperationException.class)
    public void fromJSON() throws ParseException {
        JSONConvert.EMPTY.fromJSON("any");
    }
}