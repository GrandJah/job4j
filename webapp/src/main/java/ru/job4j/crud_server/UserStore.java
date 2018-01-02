package ru.job4j.crud_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5423/job4j",
                    "postgres", "12345678");
            this.connection.setAutoCommit(false);
            Statement db = connection.createStatement();
            db.executeUpdate("CREATE TABLE if not exists Users_store ("
                    + "login VARCHAR(30) PRIMARY KEY UNIQUE, name VARCHAR(50), email VARCHAR(30), created TIMESTAMP)");
            this.connection.commit();
            db.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        try (Statement db = this.connection.createStatement()) {
            System.out.println("SELECT from Users_store WHERE login = '" + login + "'");
            ResultSet rs = db.executeQuery("SELECT * from Users_store WHERE login = '" + login + "'");
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
        boolean success = false;
        try (PreparedStatement db = this.connection.prepareStatement(
                "INSERT  INTO  users_store VALUES (?, ?, ?, ?)")) {
            db.setString(1, login);
            db.setString(2, name);
            db.setString(3, email);
            db.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            db.addBatch();
            try {
                db.execute();
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

    /** Delete user if exist.
     * @param login login
     */
    public void deleteUser(String login) {
        try (PreparedStatement db = this.connection.prepareStatement("DELETE FROM users_store WHERE login = ?")) {
            db.setString(1, login);
            db.addBatch();
            db.execute();
            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Update user if exist.
     * @param login login
     * @param name name
     * @param email email
     */
    public void updateUser(String login, String name, String email) {
        System.out.println(login + name + email);
        try (PreparedStatement db = this.connection.prepareStatement(
                "UPDATE users_store SET name = ?, email =  ? WHERE login = ?")) {
            db.setString(1, name);
            db.setString(2, email);
            db.setString(3, login);
            db.addBatch();
            db.execute();
            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
