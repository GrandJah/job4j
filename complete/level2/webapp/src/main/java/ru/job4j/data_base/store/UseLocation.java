package ru.job4j.data_base.store;

import ru.job4j.data_base.db_interface.DBInterface;
import ru.job4j.data_base.db_interface.DataBasePool;
import ru.job4j.data_base.model.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Use Location store.
 */
public class UseLocation {
    /**
     * date base interface.
     */
    private static final DBInterface DB = new DataBasePool();

    /** get location.
     * @param login login
     * @return location
     */
    public Location getUserLocation(String login) {
        final Location[] location = {Location.UNKNOWN};
        DB.goDB("select city, co.country as country from user_location as l join users as u on u.id = l.user_id join cities as c on c.id = l.city_id join countries as co on co.id = c.country where u.login = ?",
                (ResultSet rs) -> {
                    try {
                        while (rs.next()) {
                            location[0] = Location.valueOf(String.format("%s/%s", rs.getString("city"), rs.getString("country")));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }, login);
        return location[0];
    }

    /** Add location user.
     * @param login login user
     * @param location location;
     */
    private void setUserLocation(String login, Location location) {
        DB.goDB("INSERT INTO user_location (user_id, city_id) VALUES ((select id FROM users WHERE login = ?),(SELECT id FROM cities WHERE city = ? AND country = (SELECT id FROM countries where countries.country = ?)))",
                login, location.getCity(), location.geCountry());
    }

    /** delete location.
     * @param login login
     */
    private void deleteLocation(String login) {
        DB.goDB("delete from user_location where user_id = (select id from users where login = ?)", login);
    }

    /** update location.
     * @param login login
     * @param location location
     */
    public void updateLocation(String login, Location location) {
        deleteLocation(login);
        if (location != Location.UNKNOWN) {
            setUserLocation(login, location);
        }
    }
}
