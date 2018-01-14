package ru.job4j.interface_servlet;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;

import ru.job4j.crud_server.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.01.2018
 */
public class UserStore {

    /**
     * Singleton instance.
     */
    private static UserStore instance = new UserStore();

    /**
     * @return instance
     */
    public static UserStore getInstance() {
        return instance;
    }

    /**
     * Create connection's pool.
     */
    private UserStore() {
//        Class.forName("org.postgresql.Driver");
        new PoolableConnectionFactory(
                new DriverManagerConnectionFactory(
                        "jdbc:postgresql://localhost:5423/job4j",
                        "postgres", "12345678"),
                new GenericObjectPool() { {
                    new PoolingDriver().registerPool("user_store", this);
                } }, null,
                "CREATE TABLE if not exists Users_store "
                        + "(login VARCHAR(30) PRIMARY KEY UNIQUE,"
                        + " name VARCHAR(50), email VARCHAR(30), created TIMESTAMP)",
                false, false);
    }

    /** Get User.
     * @param login login
     * @return User
     */
    public User getUser(String login) {
        return login == null ? User.UNKNOWN : getUsers(login).get(0);
    }

    /** Get Users.
     * @param logins logins
     * @return List Users
     */
    public List<User> getUsers(String ... logins) {
        final List<User> users = new ArrayList<>();
        String query = "SELECT * from Users_store";
        if (logins != null && logins.length > 0) {
            query += " WHERE login = ?";
            if (logins.length > 1) {
                for (int i = 0; i < logins.length - 1; i++) {
                    query += " OR login = ?";
                }
            }
        }
        goDB(query, rs -> {
            try {
                while (rs.next()) {
                    Timestamp created = rs.getTimestamp("created");
                    users.add(new User(rs.getString("login"), rs.getString("name"),
                            rs.getString("email"), created == null ? 0 : created.getTime()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, logins);
        if (users.size() == 0) {
            users.add(User.UNKNOWN);
        }
        return users;
    }


    /** Add User.
     * @param login login
     * @param name name
     * @param email email
     * @return true if added new User
     */
    public boolean addUser(String login, String name, String email) {
        return goDB("INSERT  INTO  users_store VALUES (?, ?, ?, ?)",
                login, name, email, new Timestamp(System.currentTimeMillis()));
    }

    /** Delete user if exist.
     * @param login login
     */
    public void deleteUser(String login) {
        goDB("DELETE FROM users_store WHERE login = ?", login);
    }

    /** Update user if exist.
     * @param login login
     * @param name name
     * @param email email
     */
    public void updateUser(String login, String name, String email) {
        goDB("UPDATE users_store SET name = ?, email =  ? WHERE login = ?",
                name, email, login);
    }

    /**
     * @param query query
     * @param params parameters
     * @return true if commit is success
     */
    private boolean goDB(String query, Object ... params) {
        return goDB(query, null, params);
    }

    /**
     * @param query query
     * @param function function
     * @param params parameters
     * @return true if commit is success
     */
    private boolean goDB(String query, Function<ResultSet, Void> function, Object ... params) {
        boolean success = false;
        try (Connection connection = DriverManager.getConnection("jdbc:apache:commons:dbcp:user_store")) {
            try (PreparedStatement db = connection.prepareStatement(query)) {
                ParameterMetaData meta = db.getParameterMetaData();
                for (int i = 1; i <= params.length; i++) {
                    db.setObject(i, params[i - 1], meta.getParameterType(i));
                }
                try {
                    db.execute();
                    connection.commit();
                    if (function != null) {
                        function.apply(db.getResultSet());
                    }
                    success = true;
                } catch (SQLException e) {
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}

