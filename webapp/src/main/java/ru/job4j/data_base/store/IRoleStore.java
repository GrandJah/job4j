package ru.job4j.data_base.store;

import ru.job4j.data_base.model.Role;

import java.util.Set;

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

    /**
     * Все роли.
     * @return list of all roles.
     */
    Set<Role> getRoles();
}
