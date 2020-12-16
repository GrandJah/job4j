package ru.job4j.webtest.dao;

/**
 * test class.
 */
public class UserModelDaoTest {
//    /**
//     * Full Test.
//     */
//    @Test(expected = UnsupportedOperationException.class)
//    public void whenFullTest() {
//        RoleModel role = new RoleModelDao().create("RoleTest");
//        UserModelDao userModelDao = new UserModelDao();
//        AddressModel addressModel = new AddressModelDao().create("Address");
//        UserModel userModel = userModelDao.create("Login", addressModel.getId(), role.getId());
//        Collection collection = userModelDao.readAll();
//        assertEquals(collection.contains(userModel), true);
//        UserModel newUserModel = userModelDao.read(userModel.getId());
//        AddressModel newAddressModel = new AddressModelDao().create("newAddress");
//        newUserModel.setLogin("NewLogin");
//        newUserModel.setAddress(newAddressModel.getId());
//        userModelDao.update(newUserModel);
//        collection = userModelDao.readAll();
//        assertEquals(collection.contains(userModel), false);
//        assertEquals(collection.contains(newUserModel), true);
//        userModelDao.delete(userModel.getId());
//        collection = userModelDao.readAll();
//        assertEquals(collection.contains(userModel), false);
//        assertEquals(collection.contains(newUserModel), false);
//        userModelDao.insert(newUserModel);
//    }
}
