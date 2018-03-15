package ru.job4j.webtest.model;

/**
 * UserModel.
 */
public class UserModel extends Model {
    /**
     * user login.
     */
    private String login;

    /**
     * user address.
     */
    private int address;

    /**
     * role user.
     */
    private int role;

    /**
     * @param id id model
     * @param login user login
     * @param address user address
     * @param role role roles
     */
    public UserModel(int id, String login, int address, int role) {
        super(id);
        this.login = login;
        this.address = address;
        this.role = role;
    }

    /**
     * @return address
     */
    public int getAddress() {
        return address;
    }

    /**
     * @param address address
     */
    public void setAddress(int address) {
        this.address = address;
    }

    /** getter.
     * @return role.
     */
    public int getRole() {
        return role;
    }

    /** setter.
     * @param role newRole
     */
    public void setRole(int role) {
        this.role = role;
    }

    /** getter.
     * @return user login
     */
    public String getLogin() {
        return login;
    }

    /** setter.
     * @param login user login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || ((o != null && getClass() == o.getClass()) && this.login.equals(((UserModel) o).login));
    }

    @Override
    public int hashCode() {
        return this.login.hashCode();
    }
}
