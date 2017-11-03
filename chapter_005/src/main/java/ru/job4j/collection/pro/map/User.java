package ru.job4j.collection.pro.map;

import java.util.Calendar;
import java.util.Date;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.11.2017
 */
public class User {
    /**
     * Имя пользователя.
     */
    private String name;
    /**
     * Кол-во детей.
     */
    private int children;
    /**
     * День рождения.
     */
    private Calendar birthday;

    /**
     * @param name Имя
     * @param children Дети
     * @param birthday День Рождения
     */
    public User(String name, int children, Date birthday) {
        this.name = name;
        this.children = children;
        this.birthday = Calendar.getInstance();
        this.birthday.setTime(birthday);
    }

    /**
     * @param o сравниваемый объект.
     * @return equals для User
     */
    public boolean equalsUserTest(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (children != user.children) {
            return false;
        }
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        return birthday != null ? birthday.equals(user.birthday) : user.birthday == null;
    }

    /**
     * @return hashCode для User
     */
    public int hashUserTest() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + children;
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }
}
