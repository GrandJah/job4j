package ru.job4j.webtest.dao;

import ru.job4j.webtest.model.MusicTypeModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * DAO MusicTypeModel class.
 */
public class MusicTypeDao extends AbstractModelDao<MusicTypeModel> {
    /**
     * Create DAO music type.
     */
    public MusicTypeDao() {
        super("musictypes");
    }

    /** Create MusicTypeModel Object.
     * @param musictype music type
     * @return MusicTypeModel Object
     */
    public MusicTypeModel create(String musictype) {
        return insert(new MusicTypeModel(0, musictype));
    }

    @Override
    Object[] params(MusicTypeModel entity) {
        return new Object[]{entity.getType()};
    }

    @Override
    MusicTypeModel convert(ResultSet rs) throws SQLException {
        return new MusicTypeModel(rs.getInt("id"), rs.getString("type"));
    }

    @Override
    String getQueryInsertPartitionFieldAndValues() {
        return "(type) values (?)";
    }

    @Override
    String getQueryUpdatePartitionSet() {
        return "type = ?";
    }

    /** find by field types.
     * @param type types
     * @return types
     */
    public Collection<MusicTypeModel> findByType(String type) {
        return find("type = ?", type);
    }

    /** get musictypes user's.
     * @param user user id
     * @return music types
     */
    public Collection<MusicTypeModel> getAllTypesForUser(int user) {
        final LinkedList<MusicTypeModel> list = new LinkedList<>();
        DB.goDB("select m.id as id, m.type as type from musictypes m "
                        + "RIGHT JOIN usermusic u ON u.musictype = m.id WHERE u.user_id = ?",
                rs -> {
                    while (rs.next()) {
                        list.add(convert(rs));
                    }
                }, user);
        return list;
    }
}
