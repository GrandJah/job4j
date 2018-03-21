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
public class TeacherTest {
    /**
     * Test.
     */
    @Test
    public void whenTeachIraThenOk() {
        Teacher teacher = new Teacher("Петрова", 78);
        Ira ira = new Ira();
        assertThat(teacher.teach(ira), is("Учитель Петрова учит ученика Ира"));
    }

    /**
     * Ира это ученица.
     */
    private class Ira implements Student {
        @Override
        public String getName() {
            return "Ира";
        }
    }
}