package ru.job4j.condition;

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
public class PointTest {
	/**
	* Test is.
	*/
	@Test
	public  void whenZeroPointToTwoandThreeThenFalse() {
		Point point = new Point(0, 0);
		assertThat(point.is(2, 3), is(false));
	}
	/**
	* Test is.
	*/
	@Test
	public  void whenTwoFivePointToOneAndThreeThenTrue() {
		Point point = new Point(2, 5);
		assertThat(point.is(1, 3), is(true));
	}
}