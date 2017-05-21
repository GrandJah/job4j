package ru.job4j.calculator;

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
public class CalculatorTest {
	/**
	* Test add.
	*/
	@Test
	public  void whenAddTwoPlusThreeThenFive() {
		Calculator calc = new Calculator();
		calc.add(2, 3);
		double result = calc.getResult();
		double expected = 5;
		assertThat(result, is(expected));
	}
	/**
	* Test substruct.
	*/
	@Test
	public  void whenSubstructTwoMinusThreeThenMinusOne() {
		Calculator calc = new Calculator();
		calc.substruct(2, 3);
		double result = calc.getResult();
		double expected = -1;
		assertThat(result, is(expected));
	}
	/**
	* Test div.
	*/
	@Test
	public  void whenThreeDivFiveThenZeroPointSix() {
		Calculator calc = new Calculator();
		calc.div(3, 5);
		double result = calc.getResult();
		double expected = 0.6;
		assertThat(result, is(expected));
	}
	/**
	* Test div by zero.
	*/
	@Test
	public  void whenDivByZeroThenEx() {
		Calculator calc = new Calculator();
		calc.div(3, 0);
		double result = calc.getResult();
		double expected = Double.POSITIVE_INFINITY;
		assertThat(result, is(expected));
	}
	/**
	* Test multiple.
	*/
	@Test
	public  void whenTwoMultipleThreeThenSix() {
		Calculator calc = new Calculator();
		calc.multiple(2, 3);
		double result = calc.getResult();
		double expected = 6;
		assertThat(result, is(expected));
	}
}