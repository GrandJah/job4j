package ru.job4j.webtest;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * test class.
 */
public class UserTest {

    /**
     * test exception.
     */
    @Test
    public void whenOneRoleThenOneHash() {
        Role role = Role.valueOf("USER");
        Role admin = Role.valueOf("ADMIN");
        String address = "addressHash";
        User newUser =  User.newUser("testHash", address, role);
        User user =  User.valueOf("testHash");
        assertEquals(newUser.hashCode(), user.hashCode());
        assertEquals(0, User.EMPTY.hashCode());
        assertEquals(User.EMPTY, User.valueOf(null));
        assertEquals(role, user.getRole());
        assertEquals(address, user.getAddress());
        user.changeRole(admin);
        assertEquals(admin, user.getRole());
        MusicType music = MusicType.valueOf("JAZZ");
        user.addMusicTypes(music);
        assertArrayEquals(new MusicType[]{music}, user.getTypes());
    }
}