package ru.job4j.webtest;

import ru.job4j.webtest.dao.MusicTypeDao;
import ru.job4j.webtest.model.MusicTypeModel;

import java.util.LinkedList;

/**
 * Music type class.
 */
public class MusicType {
    /**
     * Empty object.
     */
    public static final MusicType EMPTY = new MusicType(null);

    /**
     * RoleModel.
     */
    private MusicTypeModel type;

    /**
     * @return type id.
     */
    public Object getId() {
        return this.type.getId();
    }

    /**
     * DAO.
     */
    private static final MusicTypeDao DAO = new MusicTypeDao();

    /**
     * @param type model type.
     */
    private MusicType(MusicTypeModel type) {
        this.type = type;
    }

    /**
     * @param type type.
     * @return music type
     */
    public static MusicType valueOf(String type) {
        LinkedList<MusicTypeModel> list = new LinkedList<>(MusicType.DAO.findByType(type));
        return list.size() > 0 ? new MusicType(list.getFirst()) : MusicType.EMPTY;
    }

    /** create new type.
     * @param type type
     * @return new MusicType
     */
    public static MusicType newType(String type) {
        if (valueOf(type) == MusicType.EMPTY) {
            throw new IllegalArgumentException();
        }
        return new MusicType(MusicType.DAO.create(type));
    }

    /** get all music types user.
     * @param user type
     * @return users
     */
    public static MusicType[] getAllMusicTypes(User user) {
        LinkedList<MusicTypeModel> list = new LinkedList<>(MusicType.DAO.getAllTypesForUser(user.getId()));
        MusicType[] types = new MusicType[list.size()]; int i = 0;
        for (MusicTypeModel model : list) {
            types[i++] = new MusicType(model);
        }
        return types;
    }
}
