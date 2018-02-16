package ru.job4j.user_store;

import java.util.List;

/**
 * Interface User Store.
 */
public interface IUserStore {
    /** Get User.
     * @param login login
     * @return User (User.UNKNOWN if empty)
     */
    User getUser(String login);

    /** Get Users.
     * @param logins logins
     * @return List Users
     */
    List<User> getUsers(String ... logins);

    /** Add User.
     * @param login login
     * @param name name
     * @param email email
     * @return true if added new User
     */
    boolean addUser(String login, String name, String email);

    /** Delete user if exist.
     * @param login login
     */
    void deleteUser(String login);

    /** Update user if exist.
     * @param login login
     * @param name name
     * @param email email
     */
    void updateUser(String login, String name, String email);

    /** Выборка роли пользователя.
     * @param login login
     * @return role
     */
    Role getRole(String login);

    /** Установка роли для пользователя.
     * @param login login
     * @param role role
     */
    void setUser(String login, Role role);
}
