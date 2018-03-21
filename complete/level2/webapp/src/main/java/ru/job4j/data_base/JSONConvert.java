package ru.job4j.data_base;

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
     */
    default void fromJSON(String string) {
        throw new UnsupportedOperationException();
    }
}
