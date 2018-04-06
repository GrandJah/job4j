package ru.job4j.sell_car.models;

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
     * @return User
     */
    public static User findUser(String login) {
        try (Hibernate hibernate = new Hibernate()) {
            return hibernate.getSingle("from User where login = ?", User.class, login);
        }
    }

    /** add new User.
     * @param login login
     * @param password password
     * @return true if create new user
     */
    public static boolean newUser(String login, String password) {
        boolean result = false;
        if (findUser(login) == null) {
            try (Hibernate hibernate = new Hibernate()) {
                hibernate.save(new User(login, password));
            }
            result = (findUser(login) != null);
        }
        return result;
    }

    /** check pass.
     * @param password password.
     * @return check result
     */
    public boolean checkPass(String password) {
        return this.password.equals(password);
    }
}
