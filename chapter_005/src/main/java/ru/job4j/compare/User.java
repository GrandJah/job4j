package ru.job4j.compare;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.06.2017
 */
public class User implements Comparable<User> {
    /**
     * Имя.
     */
    private String name;
    /**
     * Возрвст.
     */
    private int age;

    /** Пользователь.
     * @param name имя
     * @param age возраст
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
     * @return age age
     */
    public int getAge() {
        return this.age;
    }

    @Override
    public int compareTo(User o) {
        return this.age == o.age ? 0 : this.age < o.age ? -1 : 1;
    }

    @Override
    public String toString() {
        return String.format("User{name='%s', age='%d'}", this.name, this.age);
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
        return age == user.age && (name != null ? name.equals(user.name) : user.name == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}
