package ru.job4j.tracker;


import ru.job4j.tracker.adapters.StoreToTrackerAdapter;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 20.12.2017
 */
public class TrackerDBTest extends TrackerTest {

    /**
     *  Настройка класса трекера на БД.
     */
    public TrackerDBTest() {
        super(new StoreToTrackerAdapter(new HbmTracker()));
    }
}
