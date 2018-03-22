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
        AddressModelDao addressModelDao = new AddressModelDao();
        AddressModel addressModel = addressModelDao.create("USSR");
        Collection collection = addressModelDao.readAll();
        assertEquals(collection.contains(addressModel), true);
        AddressModel newAddressModel = addressModelDao.read(addressModel.getId());
        newAddressModel.setAddress("RF");
        addressModelDao.update(newAddressModel);
        collection = addressModelDao.readAll();
        assertEquals(collection.contains(addressModel), false);
        assertEquals(collection.contains(newAddressModel), true);
        addressModelDao.delete(addressModel.getId());
        collection = addressModelDao.readAll();
        assertEquals(collection.contains(addressModel), false);
        assertEquals(collection.contains(newAddressModel), false);
        addressModelDao.insert(newAddressModel);
    }
}