package ru.job4j.start;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 28.05.2017
 */
class MenuOutException extends RuntimeException {
    /**
     * @param message сообщение об ошибке
     */
    MenuOutException(String message) {
        super(message);
    }
}
