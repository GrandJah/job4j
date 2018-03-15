package ru.job4j.webtest.dao;

import ru.job4j.webtest.MusicType;
import ru.job4j.webtest.Role;
import ru.job4j.webtest.model.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * User DAO class.
 */
public class UserModelDao extends AbstractModelDao<UserModel> {
    /**
     * Create DAO user.
     */
    public UserModelDao() {
        super("users");
    }

    /** Create UserModel Object.
     * @param login login UserModel
     * @param refAddress reference address model
     * @param role role
     * @return UserModel
     */
    public UserModel create(String login, int refAddress, int role) {
        return insert(new UserModel(0, login, refAddress, role));
    }

    @Override
    Object[] params(UserModel entity) {
        return new Object[]{entity.getLogin(), entity.getAddress(), entity.getRole()};
    }

    @Override
    UserModel convert(ResultSet rs) throws SQLException {
        return new UserModel(rs.getInt("id"), rs.getString("login"), rs.getInt("address"), rs.getInt("role"));
    }

    @Override
    String getQueryInsertPartitionFieldAndValues() {
        return "(login, address, role) values (?,?,?)";
    }

    @Override
    String getQueryUpdatePartitionSet() {
        return "login = ?, address = ?, role = ?";
    }

    /** find by login.
     * @param login login.
     * @return Users.
     */
    public Collection<UserModel> findByLogin(String login) {
        return find("login = ?", login);
    }

    /** find by field.
     * @param address address   (nullable)
     * @param role role         (nullable)
     * @param type music type   (nullable)
     * @return Users
     */
    public Collection<UserModel> findByField(String address, Role role, MusicType type) {
        final LinkedList<UserModel> list = new LinkedList<>();
        LinkedList<Object> param = new LinkedList<>();
        StringBuilder query = new StringBuilder("select u.id as id, u.login as login, u.role as role, u.address as address from users u");
        StringBuilder expression = new StringBuilder(" where");
        boolean p = false;
        if (address != null) {
            query.append(" JOIN addresses a ON u.address = a.id");
            expression.append(" a.address = ?");
            param.add(address);
            p = true;
        }
        if (role != null) {
            query.append(" JOIN roles r ON r.id = u.role");
            expression.append(p ? "AND" : "").append(" r.id = ?");
            param.add(role.getId());
            p = true;
        }
        if (type != null) {
            query.append(" LEFT JOIN usermusic mu ON mu.user_id = u.id LEFT JOIN musictypes m ON mu.musictype = m.id");
            expression.append(p ? "AND" : "").append(" m.id = ?");
            param.add(type.getId());
        }
        DB.goDB(query.append(expression).toString(), rs -> {
            while (rs.next()) {
                list.add(convert(rs));
            }
        }, param.toArray());
        return list;
    }
}
