package ru.job4j.calculator;
	/**
	* Класс калькуятор.
	* @author Igor Kovalkov
	* @since 21.05.2017
	* @version 1
	*/
public class Calculator {
	/** Результат. */
	private double result;
	/**
	* Сложение.
	* @param first первый аргумент
	* @param second второй аргумент
	*/
public void add(double first, double second) {
	this.result = first + second;
	}
	/**
	* Вычитание.
	* @param first первый аргумент
	* @param second второй аргумент
	*/
public void substruct(double first, double second) {
	this.result = first - second;
	}
	/**
	* Деление.
	* @param first первый аргумент
	* @param second второй аргумент
	*/
public void div(double first, double second) {
	this.result = first / second;
	}
	/**
	* Умножение.
	* @param first первый аргумент
	* @param second второй аргумент
	*/
public void multiple(double first, double second) {
	this.result = first * second;
	}
	/**
	* Результат.
	* @return результат
	*/
public double getResult() {
	return this.result;
	}
}