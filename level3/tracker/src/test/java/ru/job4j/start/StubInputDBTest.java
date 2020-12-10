package ru.job4j.start;

import ru.job4j.tracker.TrackerDB;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.12.2017
 */
public class StubInputDBTest extends StubInputTest {
    /**
     * Настройка класса трекера на БД.
     */
    public StubInputDBTest() {
        setTracker(new TrackerDB("src/test/config/test.config"));
    }
}
