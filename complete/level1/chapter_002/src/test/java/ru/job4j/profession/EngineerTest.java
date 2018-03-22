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
public class EngineerTest {
    /**
     * Test.
     */
    @Test
    public void whenCalculationThenOk() {
        Engineer engineer = new Engineer("Сидоров");
        Task task = new Task();
        assertThat(engineer.calculation(task), is("Инжинер Сидоров производит расчеты Проект века!"));
    }

    /**
     * Test.
     */
    @Test
    public void whenReportThenReturnReport() {
        Engineer engineer = new Engineer("Сидоров");

        assertThat(engineer.report().getReport(), is("Отчет создан Сидоров"));
    }

    /**
     * Задание проект.
     */
    private class Task implements Project {
        @Override
        public String getNameDocument() {
            return "Проект века!";
        }
    }
}