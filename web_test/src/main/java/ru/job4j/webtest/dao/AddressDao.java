package ru.job4j.webtest.dao;

import ru.job4j.webtest.model.Address;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Address DAO class.
 */
public class AddressDao extends AbstractModelDao<Address> {
    /**
     * Create DAO address.
     */
    AddressDao() {
        super("addresses");
    }

    /** Create Address Object.
     * @param address address
     * @return Address Object
     */
    public Address create(String address) {
        return insert(new Address(0, address));
    }

    @Override
    Object[] params(Address entity) {
        return new Object[]{entity.getAddress()};
    }

    @Override
    Address convert(ResultSet rs) throws SQLException {
        return new Address(rs.getInt("id"), rs.getString("address"));
    }

    @Override
    String getQueryInsertPartitionFieldAndValues() {
        return "(address) values (?)";
    }

    @Override
    String getQueryUpdatePartitionSet() {
        return "address = ?";
    }
}
