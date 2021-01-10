package ru.job4j.start;

import ru.job4j.tracker.HbmTracker;
import ru.job4j.tracker.adapters.StoreToTrackerAdapter;

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
        super(new StoreToTrackerAdapter(new HbmTracker()));
    }
}
