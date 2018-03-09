package ru.job4j.data_base.model;

import ru.job4j.data_base.JSONConvert;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 02.01.2018
 */
public class User implements Comparable<User>, JSONConvert {
    /**
     * name, login, email.
     */
    private final String name, login, email;
    /**
     * created Date.
     */
    private final long createDate;

    /** Full User Constructor.
     * @param login login
     * @param name name
     * @param email email
     * @param createDate created Date
     */
    public User(String login, String name, String email, long createDate) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = createDate;
    }

    /**
     * Empty User.
     */
    public static final User UNKNOWN = new User("unknown", "", "", 0);

    /**
     * Getter.
     *
     * @return name name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter.
     *
     * @return login login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Getter.
     *
     * @return email email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Getter.
     *
     * @return createDate createDate
     */
    public long getCreateDate() {
        return this.createDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(User o) {
        return this.login.compareTo(o.login);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, email);
    }

    @Override
    public String toJSON() {
        return String.format("{\"login\":\"%s\",\"name\":\"%s\",\"email\":\"%s\"}", this.login, this.name, this.email);
    }


    private static final Pattern
            P_LOGIN = Pattern.compile("[A-Za-z]{5,15}"),
            P_NAME = Pattern.compile("[A-zА-я]{3,50}"),
            P_EMAIL = Pattern.compile("[a-z]+@[a-z]+\\.[a-z]+");

    /** validate params
     * @param login login
     * @param name name
     * @param email email
     * @return true if params correct
     */
    public static boolean validate(String login, String name, String email) {
        return P_LOGIN.matcher(login).find() && P_NAME.matcher(name).find() && P_EMAIL.matcher(email).find();
    }
}
