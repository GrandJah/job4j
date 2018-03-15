package ru.job4j.webtest;

import ru.job4j.webtest.dao.AddressModelDao;
import ru.job4j.webtest.dao.RoleModelDao;
import ru.job4j.webtest.dao.UserModelDao;
import ru.job4j.webtest.model.UserModel;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Class User.
 */
public class User {
    /**
     * Empty object.
     */
    public static final User EMPTY = new User(null);

    /**
     * UserModel.
     */
    private UserModel user;

    /**
     * @return user id.
     */
    public int getId() {
        return user.getId();
    }

    /**
     * DAO.
     */
    private static final UserModelDao DAO = new UserModelDao();

    /**
     * @param user model user.
     */
    private User(UserModel user) {
        this.user = user;
    }

    /**
     * @param login login user.
     * @return User for login
     */
    public static User valueOf(String login) {
        LinkedList<UserModel> list =
                new LinkedList<>(User.DAO.findByLogin(login));
        return list.size() > 0 ? new User(list.getFirst()) : User.EMPTY;
    }

    @Override
    public boolean equals(Object o) {
        return this == User.EMPTY ? this == o : this.user.equals(((User) o).user);
    }

    @Override
    public int hashCode() {
        return this == User.EMPTY ? 0 : this.user.hashCode();
    }

    /** crate new user.
     * @param login user name
     * @param address address
     * @param role user role
     * @return new User
     */
    public static User newUser(String login, String address, Role role) {
        AddressModelDao aDao = new AddressModelDao();
        if (valueOf(login) != User.EMPTY
                || aDao.findByAddress(address).size() != 0) {
            throw new IllegalArgumentException();
        }
        return new User(User.DAO.create(login, aDao.create(address).getId(), role.getId()));
    }

    /** change role.
     * @param role role
     */
    public void changeRole(Role role) {
        this.user.setRole(role.getId());
        User.DAO.update(this.user);
    }

    /** get role.
     * @return User
     */
    public Role getRole() {
        return Role.valueOf(new RoleModelDao().read(this.user.getRole()).getName());
    }

    /** get address.
     * @return User
     */
    public String getAddress() {
        return new AddressModelDao().read(this.user.getAddress()).getAddress();
    }

    /**
     * @return music types user's
     */
    public MusicType[] getTypes() {
        return MusicType.getAllMusicTypes(this);
    }


    /** find User.
     * @param address address.
     * @param role role
     * @param type music type
     * @return Users
     */
    public static User[] findByFild(String address, Role role, MusicType type) {
        Collection<UserModel> collection = User.DAO.findByField(address, role, type);
        User[] users = new User[collection.size()]; int i = 0;
        for (UserModel model : collection) {
            users[i++] = new User(model);
        }
        return users;
    }
}
