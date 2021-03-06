package ru.job4j.data_base.store;

import ru.job4j.data_base.db_interface.DBInterface;
import ru.job4j.data_base.db_interface.DataBasePool;
import ru.job4j.data_base.model.User;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
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
     * db_interface connection.
     */
    private static DBInterface db = new DataBasePool();

    @Override
    public User getUser(String login) {
        return login == null ? User.UNKNOWN : getUsers(login).get(0);
    }

    @Override
    public List<User> getUsers(String ... logins) {
        final List<User> users = new ArrayList<>();
        StringBuilder builder = new StringBuilder("SELECT * from users");
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
        }, (Object[]) logins);
        if (users.size() == 0) {
            users.add(User.UNKNOWN);
        }
        return users;
    }

    @Override
    public boolean addUser(String login, String name, String email) {
        return db.goDB("INSERT INTO users (login, name, email, created) VALUES (?, ?, ?, ?)",
                login, name, email, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void deleteUser(String login) {
        db.goDB("DELETE FROM users WHERE login = ?", login);
    }

    @Override
    public void updateUser(String login, String name, String email) {
        db.goDB("UPDATE users SET name = ?, email =  ? WHERE login = ?",
                name, email, login);
    }

    @Override
    public Iterator<User> iterator() {
        return getUsers().iterator();
    }
}

