package ru.job4j.webtest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * test class.
 */
public class MusicTypeTest {
    /**
     * full test.
     */
    @Test
    public void name() {
        MusicType type;
        type = MusicType.valueOf("testType");
        assertEquals(MusicType.EMPTY, type);
        type = MusicType.newType("testType");
        assertNotEquals(MusicType.EMPTY, type);
    }

    /**
     * test exception.
     */
    @Test
    public void whenOneTypeThenOneHash() {
        MusicType newType =  MusicType.newType("testHash");
        MusicType type =  MusicType.valueOf("testHash");
        assertEquals(newType.hashCode(), type.hashCode());
        assertEquals(0, MusicType.EMPTY.hashCode());
        assertEquals(MusicType.EMPTY, MusicType.valueOf(null));
    }

    /**
     * test exception.
     */
    @Test (expected = IllegalArgumentException.class)
    public void whenCreatingTypeIfPresentThenThrowException() {
        MusicType.newType("testTypeCreate");
        MusicType.newType("testTypeCreate");
    }
}