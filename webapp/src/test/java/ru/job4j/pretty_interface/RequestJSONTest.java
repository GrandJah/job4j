package ru.job4j.pretty_interface;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 08.03.2018
 */
public class RequestJSONTest {

    /**
     * Test method.
     */
    @Test
    public void whenJSONStringifyAndParseThenOk() {
        RequestJSON requestJSON = new RequestJSON();
        String string = "{\"action\":\"table\",\"data\":{}}";
        requestJSON.fromJSON(string);
        assertEquals(string, requestJSON.toJSON());
    }

    /**
     * Test method.
     */
    @Test
    public void whenErrorJSON() {
        RequestJSON requestJSON = new RequestJSON();
        String string = "{\'action\':\"fight\",\"data\":{}}";
        requestJSON.fromJSON(string);
        assertEquals("{\"action\":\"error\",\"data\":{}}", requestJSON.toJSON());
    }

    /**
     * Test method.
     */
    @Test
    public void whenNotRequestActionJSON() {
        RequestJSON requestJSON = new RequestJSON();
        String string = "{\"gross\":\"fight\",\"data\":{}}";
        requestJSON.fromJSON(string);
        assertEquals("{\"action\":\"error\",\"data\":{}}", requestJSON.toJSON());
    }
}