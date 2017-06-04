package ru.job4j.bank;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.06.2017
 */
public class User {
    /**
     * Имя клиента.
     */
    private String name;
    /**
     * Номер паспорта.
     */
    private int passport;

    /**
     * @param name Имя клиента
     * @param passport номер паспорта
     */
    public User(String name, int passport) {
        this.name = name;
        this.passport = passport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return this.passport == user.passport && this.name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int result = this.name.hashCode();
        result = 31 * result + this.passport;
        return result;
    }
}
