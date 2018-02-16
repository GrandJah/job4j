package ru.job4j.user_store;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.01.2018
 */
public class UserStore implements IUserStore {
    /**
     * db.
     */
    private DB db = DB.getInstance();

    /**
     * UserStoreDB - singleton.
     */
    private UserStore() { }

    /**
     *  store - singleton.
     */
    private static UserStore store = new UserStore();

    /**
     *  @return store store
     */
    public static UserStore getStore() {
        return store;
    }

    @Override
    public User getUser(String login) {
        return login == null ? User.UNKNOWN : getUsers(login).get(0);
    }

    @Override
    public List<User> getUsers(String ... logins) {
        final List<User> users = new ArrayList<>();
        StringBuilder builder = new StringBuilder("SELECT * from Users_store");
        if (logins != null && logins.length > 0) {
            builder.append(" WHERE login in (");
            if (logins.length > 1) {
                for (int i = 0; i < logins.length - 1; i++) {
                    builder.append(", ?");
                }
            }
            builder.append("?)");
        }
        String query = builder.toString();
        db.goDB(query, rs -> {
            try {
                while (rs.next()) {
                    Timestamp created = rs.getTimestamp("created");
                    users.add(new User(rs.getString("login"), rs.getString("name"),
                            rs.getString("email"), created == null ? 0 : created.getTime()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, logins);
        if (users.size() == 0) {
            users.add(User.UNKNOWN);
        }
        return users;
    }

    @Override
    public boolean addUser(String login, String name, String email) {
        return db.goDB("INSERT  INTO  users_store VALUES (?, ?, ?, ?)",
                login, name, email, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void deleteUser(String login) {
        db.goDB("DELETE FROM users_store WHERE login = ?", login);
    }

    @Override
    public void updateUser(String login, String name, String email) {
        db.goDB("UPDATE users_store SET name = ?, email =  ? WHERE login = ?",
                name, email, login);
    }

    @Override
    public Role getRole(String login) {
        final Role[] role = {Role.DefaultUser};
        db.goDB("SELECT role from UseRole WHERE user_login = ?", rs -> {
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
    public void setUser(String login, Role role) {
        db.goDB("DELETE from UseRole WHERE user_login = ?", login);
        if (role != Role.DefaultUser) {
            db.goDB("INSERT INTO UseRole VALUES(? , ?)", login, role.name());
        }
    }
}

