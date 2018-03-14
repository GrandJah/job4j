package ru.job4j.webtest.model;

/**
 * UserModel.
 */
public abstract class UserModel extends Model {
    /**
     * user login.
     */
    private String login;

    /**
     * user address.
     */
    private int address;

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

    /**
     * @param id id model
     * @param login user login
     * @param address user address
     */
    public UserModel(int id, String login, int address) {
        super(id);
        this.login = login;
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
