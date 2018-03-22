package ru.job4j.orderbook;

/**
 * order attribute class .
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.11.2017
 */
final class Attribute {
    /**
     * BUY|SELL.
     */
    private boolean buy;
    /**
     * price and volume.
     */
    private int price, volume;

    /**
     * @param buy isBuy
     * @param price price
     * @param volume volume
     */
    Attribute(boolean buy, double price, int volume) {
        this.buy = buy;
        this.price = (int) (price * 100);
        this.volume = volume;
    }

    /**
     * Getter.
     *
     * @return price price
     */
    int getPrice() {
        return this.price;
    }

    /**
     * Getter.
     *
     * @return volume volume
     */
    int getVolume() {
        return this.volume;
    }

    /**
     * Getter.
     *
     * @return buy buy
     */
    boolean isBuy() {
        return this.buy;
    }
}
