package ru.job4j.collection.pro.map;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 04.11.2017
 */
public class TestUserMap {
    /** тест поведения объекта с различным набором переопределения hashCode и equals.
     * @param args args
     */
    public static void main(String[] args) {
        TestUserMap test = new TestUserMap();
        Date birthday = new Date(1986, 07, 11);
        User user1, user2;

        //Без переопределиения hashCode и equals
        user1 = new User("Alex", 1, birthday);
        user2 = new User("Alex", 1, birthday);
        test.print("User - ", test.createMap(user1, user2));

        //Переопределение только hashCode
        user1 = new HashUser("Alex", 1, birthday);
        user2 = new HashUser("Alex", 1, birthday);
        test.print("HashUser - ", test.createMap(user1, user2));

        //Переопределение только equals
        user1 = new EqualsUser("Alex", 1, birthday);
        user2 = new EqualsUser("Alex", 1, birthday);
        test.print("EqualsUser - ", test.createMap(user1, user2));

        //Переопределение hashCode и equals
        user1 = new FullUser("Alex", 1, birthday);
        user2 = new FullUser("Alex", 1, birthday);
        test.print("FullUser - ", test.createMap(user1, user2));
    }

    /** Вывод на печать карты.
     * @param append Строка определения
     * @param map карта для вывода
     */
    private void print(String append, Map map) {
        System.out.println(append);
        System.out.println(map);
        System.out.println(System.lineSeparator());
    }

    /** создания карты на основании двух объектов User.
     * @param user1 user1
     * @param user2 user2
     * @return созданная карта
     */
    private Map<User, Object> createMap(User user1, User user2) {
        Map<User, Object> map = new HashMap<>();
        map.put(user1, null);
        map.put(user2, null);
        return map;
    }

    /**
     * класс с переопределенным hashCode.
     */
    static class HashUser extends User {

        /**
         * @param name     Имя
         * @param children Дети
         * @param birthday День Рождения
         */
        HashUser(String name, int children, Date birthday) {
            super(name, children, birthday);
        }

        @Override
        public int hashCode() {
            return super.hashUserTest();
        }
    }

    /**
     * класс с переопределенным equals.
     */
    static class EqualsUser extends User {

        /**
         * @param name     Имя
         * @param children Дети
         * @param birthday День Рождения
         */
        EqualsUser(String name, int children, Date birthday) {
            super(name, children, birthday);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equalsUserTest(obj);
        }
    }

    /**
     * класс с переопределенными hashCode и equals.
     */
    static class FullUser extends User {

        /**
         * @param name     Имя
         * @param children Дети
         * @param birthday День Рождения
         */
        FullUser(String name, int children, Date birthday) {
            super(name, children, birthday);
        }

        @Override
        public boolean equals(Object obj) {
            return super.equalsUserTest(obj);
        }

        @Override
        public int hashCode() {
            return super.hashUserTest();
        }
    }
}
