package ru.job4j.board.exception;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.05.2017
 */
public class OccupiedWayException extends BoardException {
    /**
     * Default constructor.
     */
    public OccupiedWayException() {
        super("Occupied way");
    }
}
