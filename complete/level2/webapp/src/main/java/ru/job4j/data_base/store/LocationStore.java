package ru.job4j.data_base.store;

import ru.job4j.data_base.db_interface.DBInterface;
import ru.job4j.data_base.db_interface.DataBasePool;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Location data_base interface.
 */
public class LocationStore {
    /**
     * DB interface.
     */
    private static final DBInterface DB = new DataBasePool();

    /** get all countries.
     * @return countries
     */
    public Collection<String> getCountries() {
        Collection<String> collection = new LinkedList<>();
        DB.goDB("select country from countries", (rs) -> {
            try {
                while (rs.next()) {
                    collection.add(rs.getString("country"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        return collection;
    }

    /** get cities from country.
     * @param country country
     * @return cities
     */
    public Collection<String> getCity(String country) {
        Collection<String> collection = new LinkedList<>();
        DB.goDB("select city from cities join countries on countries.id = cities.country where countries.country = ?", (rs) -> {
            try {
                while (rs.next()) {
                    collection.add(rs.getString("city"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, country);
        return collection;
    }
}
