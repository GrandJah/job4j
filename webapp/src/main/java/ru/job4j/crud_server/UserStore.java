package ru.job4j.crud_server;

import ru.job4j.user_store.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 02.01.2018
 */
public class UserStore {

    /**
     * Singleton Object.
     */
    private static final UserStore USER_STORE = new UserStore();

    /**
     * DataBase connection.
     */
    private Connection connection;

    /**
     * Default Constructor.
     */
    private UserStore() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/job4j",
                    "postgres", "postgres");
            this.connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        goDB("CREATE TABLE if not exists Users_store (login VARCHAR(30) PRIMARY KEY UNIQUE, name VARCHAR(50), email VARCHAR(30), created TIMESTAMP)");
    }

    /**
     * @return UserStore
     */
    public static UserStore getUserStore() {
        return USER_STORE;
    }

    /** Get User.
     * @param login login
     * @return User
     */
    public User getUser(String login) {
        User user = User.UNKNOWN;
        try (PreparedStatement db = this.connection.prepareStatement("SELECT * from Users_store WHERE login = ?")) {

            db.setString(1, login);
            ResultSet rs = db.executeQuery();
            if (rs.next()) {
                Timestamp created = rs.getTimestamp("created");
                user = new User(rs.getString("login"), rs.getString("name"),
                        rs.getString("email"), created == null ? 0 : created.getTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /** Add User.
     * @param login login
     * @param name name
     * @param email email
     * @return true if added new User
     */
    public boolean addUser(String login, String name, String email) {
        return goDB("INSERT  INTO  users_store VALUES (?, ?, ?, ?)", login, name, email, new Timestamp(System.currentTimeMillis()));
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
        goDB("UPDATE users_store SET name = ?, email =  ? WHERE login = ?", login, name, email);

    }

    /**
     * @param sql prepared sql string
     * @param params params
     * @return true if success
     */
    private boolean goDB(String sql, Object... params) {
        boolean success = false;
        try (PreparedStatement st = this.connection.prepareStatement(sql)) {
            ParameterMetaData meta = st.getParameterMetaData();
            for (int i = 1; i <= params.length; i++) {
                st.setObject(i, params[i - 1], meta.getParameterType(i));
            }
            try {
                st.execute();
                this.connection.commit();
                success = true;
            } catch (SQLException e) {
                this.connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}