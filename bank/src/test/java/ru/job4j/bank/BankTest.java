package ru.job4j.bank;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class BankTest {
    /**
     * Test method.
     */
    @Test
    public void whenTwoRangeThenTwoRangeReturn() {
    Client[] clients = {
            new Client(new Time(9, 40), new Time(10, 0)),
            new Client(new Time(9, 20), new Time(11, 20)),
            new Client(new Time(10, 20), new Time(12, 20)),
            new Client(new Time(9, 20), new Time(12, 20)) };
        Bank bank = new Bank(clients);
        Time[][] result = bank.getRangeMaxClient();
        assertThat(result, is(new Time[][]{
                {new Time(9, 40), new Time(10, 0)},
                {new Time(10, 20), new Time(11, 20)}
        }));
    }

    /**
     * Test method.
     */
    @Test
    public void whenOneClientThenOneRange() {
        Client[] clients = {
            new Client(new Time(9, 20), new Time(12, 20)) };
        Bank bank = new Bank(clients);
        Time[][] result = bank.getRangeMaxClient();
        assertThat(result, is(new Time[][]{
                {new Time(9, 20), new Time(12, 20)}
        }));
    }
}