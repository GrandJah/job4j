package ru.job4j.data_base.store;

import ru.job4j.data_base.db_interface.DBInterface;
import ru.job4j.data_base.db_interface.DataBasePool;
import ru.job4j.data_base.model.Role;

import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 20.02.2018
 */
public class RoleStore implements IRoleStore {
    /**
     * db_interface connection.
     */
    private static final DBInterface DB = new DataBasePool();

    @Override
    public Role getUserRole(String login) {
        final Role[] role = {Role.DEFAULT_USER};
        DB.goDB("SELECT roles.name from roles JOIN use_role ON use_role.role_id = roles.id JOIN users ON users.id = use_role.user_id WHERE users.login = ?", rs -> {
            try {
                while (rs.next()) {
                    role[0] = Role.valueOf(rs.getString("name"));
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
                DB.goDB("DELETE from use_role WHERE user_id = (SELECT id FROM users WHERE login = ?)", login);
        if (role != Role.DEFAULT_USER) {
            DB.goDB("INSERT INTO use_role VALUES((SELECT id FROM users WHERE login = ?), (SELECT id FROM roles WHERE name = ?))", login, role.name());
        }
    }

    @Override
    public Set<Role> getRoles() {
        final Set<Role> roles = new TreeSet<>();
        DB.goDB("SELECT * from roles", rs -> {
            try {
                while (rs.next()) {
                    roles.add(Role.valueOf(rs.getString("name")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        return roles;
    }
}
