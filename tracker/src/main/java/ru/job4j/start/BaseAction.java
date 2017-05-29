package ru.job4j.start;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.05.2017
 */
abstract class BaseAction implements TrackerAction {
    /**
     * Информация о действии.
     */
    private String info;

    /**
     * @param info Информация о действии
     */
    BaseAction(String info) {
        this.info = info;
    }

    @Override
    public String getInfo() {
        return this.info;
    }
}
