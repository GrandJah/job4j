package ru.job4j.webtest.dao;

import ru.job4j.webtest.model.AddressModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * AddressModel DAO class.
 */
public class AddressModelDao extends AbstractModelDao<AddressModel> {
    /**
     * Create DAO address.
     */
    public AddressModelDao() {
        super("addresses");
    }

    /** Create AddressModel Object.
     * @param address address
     * @return AddressModel Object
     */
    public AddressModel create(String address) {
        return insert(new AddressModel(0, address));
    }

    @Override
    Object[] params(AddressModel entity) {
        return new Object[]{entity.getAddress()};
    }

    @Override
    AddressModel convert(ResultSet rs) throws SQLException {
        return new AddressModel(rs.getInt("id"), rs.getString("address"));
    }

    @Override
    String getQueryInsertPartitionFieldAndValues() {
        return "(address) values (?)";
    }

    @Override
    String getQueryUpdatePartitionSet() {
        return "address = ?";
    }

    /** find by field address.
     * @param address address
     * @return addresses
     */
    public Collection<AddressModel> findByAddress(String address) {
        return find("address = ?", address);
    }
}
