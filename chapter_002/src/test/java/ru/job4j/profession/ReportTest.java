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
public class ReportTest {
    /**
     * Test.
     */
    @Test
    public void whenGetNameThenName() {
        Report report = new Report("Отчет", "Содержание");
        assertThat(report.getNameDocument(), is("Отчет"));
    }

    /**
     * Test.
     */
    @Test
    public void whenGetReportThenReport() {
        Report report = new Report("Журнал", "Сводка");
        assertThat(report.getReport(), is("Сводка"));
    }

}