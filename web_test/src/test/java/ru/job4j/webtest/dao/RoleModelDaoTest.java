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
        RoleDao roleDao = new RoleDao();
        RoleModel roleModel = roleDao.create("RoleUser");
        Collection collection = roleDao.readAll();
        assertEquals(collection.contains(roleModel), true);
        RoleModel newRoleModel = roleDao.read(roleModel.getId());
        newRoleModel.setName("NewRoleUser");
        roleDao.update(newRoleModel);
        collection = roleDao.readAll();
        assertEquals(collection.contains(roleModel), false);
        assertEquals(collection.contains(newRoleModel), true);
        roleDao.delete(roleModel.getId());
        collection = roleDao.readAll();
        assertEquals(collection.contains(roleModel), false);
        assertEquals(collection.contains(newRoleModel), false);
        roleDao.insert(newRoleModel);
    }
}