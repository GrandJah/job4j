package ru.job4j.webtest.dao;

import org.junit.Test;
import ru.job4j.webtest.model.AddressModel;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * test class.
 */
public class AddressModelDaoTest {
    /**
     * Full Test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void whenFullTest() {
        AddressDao addressDao = new AddressDao();
        AddressModel addressModel = addressDao.create("USSR");
        Collection collection = addressDao.readAll();
        assertEquals(collection.contains(addressModel), true);
        AddressModel newAddressModel = addressDao.read(addressModel.getId());
        newAddressModel.setAddress("RF");
        addressDao.update(newAddressModel);
        collection = addressDao.readAll();
        assertEquals(collection.contains(addressModel), false);
        assertEquals(collection.contains(newAddressModel), true);
        addressDao.delete(addressModel.getId());
        collection = addressDao.readAll();
        assertEquals(collection.contains(addressModel), false);
        assertEquals(collection.contains(newAddressModel), false);
        addressDao.insert(newAddressModel);
    }
}