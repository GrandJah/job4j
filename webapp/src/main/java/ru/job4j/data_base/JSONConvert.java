package ru.job4j.data_base;

import org.json.simple.parser.ParseException;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 08.03.2018
 */
public interface JSONConvert {
    /**
     * Empty JSON Convert object.
     */
    JSONConvert EMPTY = () -> "{}";

    /**
     * @return string
     */
    String toJSON();

    /**
     * @param string string
     * @throws ParseException parse exception
     */
    default void fromJSON(String string) throws ParseException {
        throw new UnsupportedOperationException();
    }
}
