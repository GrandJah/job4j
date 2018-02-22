package ru.job4j.store;

import ru.job4j.store.db.DBInterface;
import ru.job4j.store.db.DataBasePool;
import ru.job4j.store.model.Role;

import java.sql.SQLException;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 20.02.2018
 */
public class RoleStore implements IRoleStore {
    /**
     * db connection.
     */
    private static final DBInterface DB = new DataBasePool();

    @Override
    public Role getUserRole(String login) {
        final Role[] role = {Role.DEFAULT_USER};
        DB.goDB("SELECT role from UseRole WHERE user_login = ?", rs -> {
            try {
                while (rs.next()) {
                    role[0] = Role.valueOf(rs.getString("role"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, login);
        return role[0];
    }

    @Override
    public void setUserRole(String login, Role role) {
        DB.goDB("DELETE from UseRole WHERE user_login = ?", login);
        if (role != Role.DEFAULT_USER) {
            DB.goDB("INSERT INTO UseRole VALUES(? , ?)", login, role.name());
        }
    }


}
