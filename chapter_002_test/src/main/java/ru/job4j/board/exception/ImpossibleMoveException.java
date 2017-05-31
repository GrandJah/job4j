package ru.job4j.board.exception;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.05.2017
 */
public class ImpossibleMoveException extends BoardException {
    /**
     * Default constructor.
     */
    public ImpossibleMoveException() {
        super("Impossible move.");
    }
}
