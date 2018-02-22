package ru.job4j.store.db;

import java.sql.ResultSet;
import java.util.function.Function;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 20.02.2018
 */
public interface DBInterface {
    /**
     * @param query query
     * @param params parameters
     * @return true if commit is success
     */
    boolean goDB(String query, Object... params);

    /**
     * @param query query
     * @param function function
     * @param params parameters
     * @return true if commit is success
     */
    boolean goDB(String query, Function<ResultSet, Void> function, Object ... params);
}
