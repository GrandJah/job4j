package ru.job4j.webtest.model;

/**
 * Address.
 */
public class Address extends Model {
    /**
     * address.
     */
    private String address;

    /**
     * @param id id model.
     * @param address address
     */
    public Address(int id, String address) {
        super(id);
        this.address = address;
    }

    /** getter.
     * @return address.
     */
    public String getAddress() {
        return address;
    }

    /** setter.
     * @param address address;
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || ((o != null && getClass() == o.getClass()) && this.address.equals(((Address) o).address));
    }

    @Override
    public int hashCode() {
        return this.address.hashCode();
    }
}
