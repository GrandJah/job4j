package ru.job4j.store;

import ru.job4j.store.model.Role;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 20.02.2018
 */
public interface IRoleStore {

    /** Выборка роли пользователя.
     * @param login login
     * @return role
     */
    Role getUserRole(String login);

    /** Установка роли для пользователя.
     * @param login login
     * @param role role
     */
    void setUserRole(String login, Role role);
}
