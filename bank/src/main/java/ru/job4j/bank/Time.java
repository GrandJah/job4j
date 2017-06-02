package ru.job4j.bank;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class Time {
    /**
     * часы от начала суток.
     */
    private int hours;
    /**
     * минуты от начала часа.
     */
    private int minutes;

    /**
     * @param hours часы от начала суток
     * @param minutes минуты от начала часа
     */
    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     * @param time время в минутах от начала суток
     */
    public Time(int time) {
        this.hours = time / 60;
        this.minutes = time % 60;
    }

    @Override
    public int hashCode() {
        return getTime();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Time && ((Time) obj).getTime() == getTime();
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", this.hours, this.minutes);
    }

    /**
     * @return время в минутах от начала суток
     */
    public int getTime() {
        return this.hours * 60 + this.minutes;
    }
}
