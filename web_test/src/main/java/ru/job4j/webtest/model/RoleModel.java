package ru.job4j.webtest.model;

/**
 * RoleModel.
 */
public class RoleModel extends Model {
    /**
     * name role.
     */
    private String name;

    /**
     * @param id id model
     * @param name name RoleModel
     */
    public RoleModel(int id, String name) {
        super(id);
        this.name = name;
    }

    /** getter.
     * @return name role.
     */
    public String getName() {
        return name;
    }

    /** setter.
     * @param name role name.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || ((o != null && getClass() == o.getClass()) && this.name.equals(((RoleModel) o).name));
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
