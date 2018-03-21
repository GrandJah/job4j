package ru.job4j.bank;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.06.2017
 */
public class User implements Comparable<User> {
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

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", passport=").append(passport);
        sb.append('}');
        return sb.toString();
    }
}
