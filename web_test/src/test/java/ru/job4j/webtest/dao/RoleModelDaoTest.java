package ru.job4j.webtest.dao;

import org.junit.Test;
import ru.job4j.webtest.model.RoleModel;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Test class.
 */
public class RoleModelDaoTest {

    /**
     * Full Test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void whenFullTest() {
        RoleModelDao roleModelDao = new RoleModelDao();
        RoleModel roleModel = roleModelDao.create("RoleUser");
        Collection collection = roleModelDao.readAll();
        assertEquals(collection.contains(roleModel), true);
        RoleModel newRoleModel = roleModelDao.read(roleModel.getId());
        newRoleModel.setName("NewRoleUser");
        roleModelDao.update(newRoleModel);
        collection = roleModelDao.readAll();
        assertEquals(collection.contains(roleModel), false);
        assertEquals(collection.contains(newRoleModel), true);
        roleModelDao.delete(roleModel.getId());
        collection = roleModelDao.readAll();
        assertEquals(collection.contains(roleModel), false);
        assertEquals(collection.contains(newRoleModel), false);
        roleModelDao.insert(newRoleModel);
    }
}