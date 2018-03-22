package ru.job4j.max;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

	/**
	* Test.
	*
	* @author Igor Kovalkov
	* @since 21.05.2017
	* @version 1
	*/
public class MaxTest {
	/**
	* Test max.
	*/
	@Test
	public  void whenTwoAndThreeThenTree() {
		Max maximum = new Max();
		assertThat(maximum.max(2, 3), is(3));
	}
	/**
	* Test max.
	*/
	@Test
	public  void whenSixAndSixThenSix() {
		Max maximum = new Max();
		assertThat(maximum.max(6, 6), is(6));
	}
	/**
	* Test max.
	*/
	@Test
	public  void whenSixAndFourAndOneThenSix() {
		Max maximum = new Max();
		assertThat(maximum.max(6, 4, 1), is(6));
	}
	/**
	* Test max.
	*/
	@Test
	public  void whenZeroAndSevenAndTwoThenSeven() {
		Max maximum = new Max();
		assertThat(maximum.max(0, 7, 2), is(7));
	}
}