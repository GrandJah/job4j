package ru.job4j.webtest.dao;

import org.junit.Test;
import ru.job4j.webtest.model.MusicTypeModel;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * test class.
 */
public class MusicTypeModelDaoTest {
    /**
     * Full Test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void whenFullTest() {
        MusicTypeDao musicTypeDao = new MusicTypeDao();
        MusicTypeModel musicTypeModel = musicTypeDao.create("ROCK");
        Collection collection = musicTypeDao.readAll();
        assertEquals(collection.contains(musicTypeModel), true);
        MusicTypeModel newMusicTypeModel = musicTypeDao.read(musicTypeModel.getId());
        newMusicTypeModel.setType("POP");
        musicTypeDao.update(newMusicTypeModel);
        collection = musicTypeDao.readAll();
        assertEquals(collection.contains(musicTypeModel), false);
        assertEquals(collection.contains(newMusicTypeModel), true);
        musicTypeDao.delete(musicTypeModel.getId());
        collection = musicTypeDao.readAll();
        assertEquals(collection.contains(musicTypeModel), false);
        assertEquals(collection.contains(newMusicTypeModel), false);
        musicTypeDao.insert(newMusicTypeModel);
    }
}