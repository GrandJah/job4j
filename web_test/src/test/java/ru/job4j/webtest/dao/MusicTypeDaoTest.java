package ru.job4j.webtest.dao;

import org.junit.Test;
import ru.job4j.webtest.model.MusicType;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * test class.
 */
public class MusicTypeDaoTest {
    /**
     * Full Test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void whenFullTest() {
        MusicTypeDao musicTypeDao = new MusicTypeDao();
        MusicType musicType = musicTypeDao.create("ROCK");
        Collection collection = musicTypeDao.readAll();
        assertEquals(collection.contains(musicType), true);
        MusicType newMusicType = musicTypeDao.read(musicType.getId());
        newMusicType.setType("POP");
        musicTypeDao.update(newMusicType);
        collection = musicTypeDao.readAll();
        assertEquals(collection.contains(musicType), false);
        assertEquals(collection.contains(newMusicType), true);
        musicTypeDao.delete(musicType.getId());
        collection = musicTypeDao.readAll();
        assertEquals(collection.contains(musicType), false);
        assertEquals(collection.contains(newMusicType), false);
        musicTypeDao.insert(newMusicType);
    }
}