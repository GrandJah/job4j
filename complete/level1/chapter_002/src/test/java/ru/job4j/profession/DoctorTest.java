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
public class DoctorTest {
    /**
     * Test.
     */
    @Test
    public void whenHealVasjaHumanThenOk() {
        Doctor doctor = new Doctor("Иванов", 2);
        Vasja vasja = new Vasja();
        assertThat(doctor.heal(vasja), is("Доктор Иванов лечит Вася"));
    }

    /**
     * Вася тоже человек.
     */
    private class Vasja implements Human {

        @Override
        public String getName() {
            return "Вася";
        }
    }
}