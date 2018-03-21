package ru.job4j.profession;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 23.05.2017
 */
public class ProfessionTest {
    /**
     * Test.
     */
    @Test
    public void whenProfessionalExtendThenGetName() {
        Profession profession = new ProfessionExtend("Имя Фамилия");
        assertThat(profession.getName(), is("Имя Фамилия"));
    }

    /**
     * Расширение абстрактного класса.
     */
    private class ProfessionExtend extends Profession {
        /**
         * @param name Название профессии
         */
        ProfessionExtend(String name) {
            super(name);
        }
    }
}