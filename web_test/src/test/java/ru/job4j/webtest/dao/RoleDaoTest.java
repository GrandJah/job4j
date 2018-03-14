package ru.job4j.webtest.dao;

import org.junit.Test;
import ru.job4j.webtest.model.Role;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Test class.
 */
public class RoleDaoTest {

    /**
     * Full Test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void whenFullTest() {
        RoleDao roleDao = new RoleDao();
        Role role = roleDao.create("RoleUser");
        Collection collection = roleDao.readAll();
        assertEquals(collection.contains(role), true);
        Role newRole = roleDao.read(role.getId());
        newRole.setName("NewRoleUser");
        roleDao.update(newRole);
        collection = roleDao.readAll();
        assertEquals(collection.contains(role), false);
        assertEquals(collection.contains(newRole), true);
        roleDao.delete(role.getId());
        collection = roleDao.readAll();
        assertEquals(collection.contains(role), false);
        assertEquals(collection.contains(newRole), false);
        roleDao.insert(newRole);
    }
}