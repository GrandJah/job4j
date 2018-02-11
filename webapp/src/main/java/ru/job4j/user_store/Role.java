package ru.job4j.user_store;

/**
 * Role Users.
 */
public enum Role {
    /**
     * Default User.
     */
    DefaultUser,
    /**
     * Administrator.
     */
    Administrator;

    /** Выборка роли пользователя.
     * @param login login
     * @return role
     */
    public static Role useRole(String login) {
        return UserStore.getStore().getRole(login);
    }

    /** Установка роли пользователю по логину.
     * @param login login
     * @param role role
     */
    public static void setRole(String login, Role role) {
        UserStore.getStore().setUser(login, role);
    }
}


