package ru.job4j.bank;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class Client {
    /**
     * Время входа посетителя.
     */
    private Time in;

    /**
     * Время выхода посетителя.
     */
    private Time out;

    /**
     * @param in Время входа посетителя.
     * @param out Время выхода посетителя.
     */
    public Client(Time in, Time out) {
        this.in = in;
        this.out = out;
    }

    /**
     * @return Время выхода посетителя.
     */
    public Time getIn() {
        return in;
    }

    /**
     * @return Время выхода посетителя.
     */
    public Time getOut() {
        return out;
    }
}
