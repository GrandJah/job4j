package ru.job4j.start;

import ru.job4j.tracker.TrackerArray;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.12.2017
 */
public class StubInputArrayTest extends StubInputTest {
    /**
     * Настройка класса трекера на БД.
     */
    public StubInputArrayTest() {
        super(new TrackerArray());
    }
}
