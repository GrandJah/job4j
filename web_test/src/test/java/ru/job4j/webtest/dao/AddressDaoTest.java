package ru.job4j.webtest.dao;

import org.junit.Test;
import ru.job4j.webtest.model.Address;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * test class.
 */
public class AddressDaoTest {
    /**
     * Full Test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void whenFullTest() {
        AddressDao addressDao = new AddressDao();
        Address address = addressDao.create("USSR");
        Collection collection = addressDao.readAll();
        assertEquals(collection.contains(address), true);
        Address newAddress = addressDao.read(address.getId());
        newAddress.setAddress("RF");
        addressDao.update(newAddress);
        collection = addressDao.readAll();
        assertEquals(collection.contains(address), false);
        assertEquals(collection.contains(newAddress), true);
        addressDao.delete(address.getId());
        collection = addressDao.readAll();
        assertEquals(collection.contains(address), false);
        assertEquals(collection.contains(newAddress), false);
        addressDao.insert(newAddress);
    }
}