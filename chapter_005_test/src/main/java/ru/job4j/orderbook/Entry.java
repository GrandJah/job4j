package ru.job4j.orderbook;

/**
 * order entry class.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.11.2017
 */
final class Entry {
    /**
     * book name.
     */
    private String book;
    /**
     * order Id.
     */
    private Integer id;

    /**
     * @param book book
     * @param  id id
     */
    Entry(String book, Integer id) {
        this.book = book;
        this.id = id;
    }

    /**
     * Getter.
     *
     * @return book book
     */
    String getBook() {
        return this.book;
    }

    /**
     * Getter.
     *
     * @return id id
     */
    Integer getId() {
        return this.id;
    }
}