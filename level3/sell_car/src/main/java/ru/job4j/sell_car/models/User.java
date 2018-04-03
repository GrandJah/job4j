package ru.job4j.sell_car.models;

import org.hibernate.query.Query;
import ru.job4j.sell_car.Hibernate;

/**
 * User models class.
 */
public class User {
    /**
     * id.
     */
    private int id;
    /**
     * login.
     */
    private String login;
    /**
     * password.
     */
    private String password;

    /** full field constructor.
     * @param login login
     * @param password password
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** select user from DB.
     * @param login login
     * @param password password
     * @return User
     */
    public static User findUser(String login, String password) {
        try (Hibernate hibernate = new Hibernate()) {
            Query<User> query = hibernate.createQuery("from User where login = :login", User.class);
            User user = null;
            for (User element : query.setParameter("login", login).list()) {
                if (element.password.equals(password)) {
                    user = element;
                    break;
                }
            }
            return user;
        }
    }

    /** add new User.
     * @param login login
     * @param password password
     * @return true if create new user
     */
    public static boolean newUser(String login, String password) {
        try (Hibernate hibernate = new Hibernate()) {
            return hibernate.save(new User(login, password)) != null;
        }
    }
}
